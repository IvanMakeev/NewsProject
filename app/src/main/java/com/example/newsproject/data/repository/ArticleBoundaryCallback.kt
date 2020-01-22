package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.api.searchRepos
import com.example.newsproject.data.database.NewsLocalCache
import com.example.newsproject.data.datasource.NetworkState
import com.example.newsproject.data.model.room.ArticleCache
import com.example.newsproject.utils.PagingRequestHelper
import java.util.concurrent.Executors

class ArticleBoundaryCallback(
    private val newsApi: NewsApi,
    private val cache: NewsLocalCache
) : PagedList.BoundaryCallback<ArticleCache>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
        private const val TOTAL_PAGE = 5
        private const val QUERY = "Android"
        private const val DATE = "2019-04-00"
        private const val SORT_BY = "publishedAt"
    }

    private var lastRequestedPage = 1
    private var isRequestedInProgress = false
    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
        get() = _networkState

    val helper = PagingRequestHelper(Executors.newCachedThreadPool())

    override fun onZeroItemsLoaded() {
        requestedAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleCache) {
        requestedAndSaveData()
    }

    private fun requestedAndSaveData() {
        if (isRequestedInProgress && lastRequestedPage > TOTAL_PAGE) return

        isRequestedInProgress = true
        _networkState.postValue(NetworkState.LOADING)
        searchRepos(
            api = newsApi,
            query = QUERY,
            date = DATE,
            sortBy = SORT_BY,
            page = lastRequestedPage,
            onSuccess = { articles ->
                cache.insertArticle(articles) {
                    lastRequestedPage++
                    isRequestedInProgress = false
                    _networkState.postValue(NetworkState.LOADED)
                }
            }, onError = { error ->
                isRequestedInProgress = false
                _networkState.postValue(NetworkState.error(error))
            }
        )
    }
}