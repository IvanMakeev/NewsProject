package com.example.newsproject.data.database

import android.util.Log
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.mapper.IMapper
import com.example.newsproject.data.model.room.ArticleCache
import java.util.concurrent.Executor

class NewsLocalCache(
    private val newsDao: NewsDao,
    private val ioExecutor: Executor,
    private val mapper: IMapper<Article, ArticleCache>
) {
    fun insertArticle(listArticles: List<Article>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", "inserting ${listArticles.size} repos")
            val listArticleCaches: MutableList<ArticleCache> = ArrayList()
            listArticleCaches.let { list ->
                listArticles.forEach {
                    list.add(mapper.mapFromJsonToRoom(it))
                }
            }
            newsDao.insertArticle(listArticleCaches)
            insertFinished()
        }
    }

    fun getArticles() = newsDao.getArticles()
}