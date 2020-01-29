package com.example.newsproject.data.database

import android.util.Log
import com.example.newsproject.data.model.json.ArticleJson
import com.example.newsproject.data.model.mapper.IMapper
import com.example.newsproject.data.model.room.ArticleRoom
import java.util.concurrent.Executor

class NewsLocalCache(
    private val newsDao: NewsDao,
    private val ioExecutor: Executor,
    private val mapper: IMapper<ArticleJson, ArticleRoom>
) {

    /**
     * Обертка над DAO для работы с БД
     */
    fun insertCache(listArticles: List<ArticleJson>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("NewsLocalCache", "inserting ${listArticles.size} articles")
            val listArticleCaches: MutableList<ArticleRoom> = ArrayList()
            listArticleCaches.let { list ->
                listArticles.forEach {
                    list.add(mapper.mapFrom(it))
                }
            }
            newsDao.insertArticles(listArticleCaches)
            insertFinished()
        }
    }

    fun fetchCache() = newsDao.fetchArticles()

    fun clearCache(){
        newsDao.clearArticles()
    }
}