package com.example.newsproject.data.repository.boundary

import androidx.paging.PagedList
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsLocalCache
import com.example.newsproject.data.model.json.ArticleJson
import com.example.newsproject.data.model.json.News
import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.data.repository.network.createStatusLiveData
import com.example.newsproject.data.repository.util.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Реализация BoundaryCallback
 */
class ArticleBoundaryCallback(
    private val newsApi: NewsApi,
    private val cache: NewsLocalCache,
    ioExecutor: Executor
) : PagedList.BoundaryCallback<ArticleRoom>() {

    companion object {
        private const val TOTAL_PAGE = 5
        private const val QUERY = "Android"
        private const val DATE = "2019-04-00"
        private const val SORT_BY = "publishedAt"
    }

    //Номер последней запрошенно страницы, страниц может быть максимум 5
    private var lastRequestedPage = 1

    val helper = PagingRequestHelper(ioExecutor)
    //Функция расширения над классом PagingRequestHelper, которая возвращает сетевые состояния
    val networkStateLiveData = helper.createStatusLiveData()

    /**
     * Метод отрабатывает, если элементов нет (первая загрузка)
     */
    override fun onZeroItemsLoaded() {
        // Метод runIfNotRunning выполняет переденный запрос, если другие запросы такого же типа не выполняются
        // Выполняется в потоке, в котором он был вызван
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            newsApi.getNews(
                query = QUERY,
                date = DATE,
                sortBy = SORT_BY,
                page = lastRequestedPage.toString()
            ).enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * Метод отрабатывает, когда элементы в бд подходят к концу.
     * Срабатывает в зависимости от того, сколько стоит префетчинг страницы (размер страницы)
     */
    override fun onItemAtEndLoaded(itemAtEnd: ArticleRoom) {
        //При достижении максимальной страницы, данные загружать дальше не нужно
        if (lastRequestedPage > TOTAL_PAGE) return
        // Метод runIfNotRunning выполняет переденный запрос, если другие запросы такого же типа не выполняются
        // Выполняется в потоке, в котором он был вызван
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            newsApi.getNews(
                query = QUERY,
                date = DATE,
                sortBy = SORT_BY,
                page = lastRequestedPage.toString()
            ).enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * Метод отвечает за создание коллбека для класса хелпера
     */
    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback): Callback<News> {
        return object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                //Сообщает хелперу что во время обновления данных произошла ошибка
                // и позволяет повторить запрос через метод retryAllFailed
                it.recordFailure(t)
            }

            override fun onResponse(
                call: Call<News>,
                response: Response<News>
            ) {
                val articles = response.body()?.articles ?: emptyList()
                insertItemsIntoDb(articles)
                //Сообщает хелперу что запрос выполнен успешно и данные извлечены
                it.recordSuccess()
            }
        }
    }

    /**
     * Метод отвечает за вставку новой порции данных в БД
     */
    private fun insertItemsIntoDb(listArticles: List<ArticleJson>) {
        cache.insertArticle(listArticles) {
            lastRequestedPage++
        }
    }
}