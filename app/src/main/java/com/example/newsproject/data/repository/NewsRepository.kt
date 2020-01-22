package com.example.newsproject.data.repository

import androidx.paging.LivePagedListBuilder
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsLocalCache

class NewsRepository(
    private val newsApi: NewsApi,
    private val cache: NewsLocalCache
) {
    companion object {
        private const val DATABASE_PAGE_SIZE = 5
    }

    fun search(): ArticleSearchResult {

        // Get data source factory from the local cache
        val dataSourceFactory = cache.getArticles()

        // Construct the boundary callback
        val boundaryCallback = ArticleBoundaryCallback(newsApi, cache)
        val networkErrors = boundaryCallback.networkState

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ArticleSearchResult(data, networkErrors, boundaryCallback.helper)
    }
}