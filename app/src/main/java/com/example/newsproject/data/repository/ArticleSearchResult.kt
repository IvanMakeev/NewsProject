
package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.newsproject.data.datasource.NetworkState
import com.example.newsproject.data.model.room.ArticleCache
import com.example.newsproject.utils.PagingRequestHelper

data class ArticleSearchResult(
    val data: LiveData<PagedList<ArticleCache>>,
    val networkState: LiveData<NetworkState>,
    val helper: PagingRequestHelper
)
