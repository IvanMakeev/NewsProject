package com.example.newsproject.data.repository

import androidx.paging.LivePagedListBuilder
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsLocalCache
import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.data.repository.boundary.ArticleBoundaryCallback
import com.example.newsproject.data.repository.core.SearchResult
import com.example.newsproject.data.repository.core.BaseRepository
import java.util.concurrent.Executor

class NewsRepository(
    private val newsApi: NewsApi,
    private val cache: NewsLocalCache,
    private val ioExecutor: Executor
) : BaseRepository {
    companion object {
        private const val DATABASE_PAGE_SIZE = 5
    }

    override fun search(): SearchResult<ArticleRoom> {
        //Получени фабрики из локального кеша
        val dataSourceFactory = cache.getArticles()

        //Конструирование boundary callback
        val boundaryCallback = ArticleBoundaryCallback(newsApi, cache, ioExecutor)
        val networkErrors = boundaryCallback.networkStateLiveData

        //Получение PagedList
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return SearchResult(
            data,
            networkErrors,
            boundaryCallback.helper
        )
    }
}