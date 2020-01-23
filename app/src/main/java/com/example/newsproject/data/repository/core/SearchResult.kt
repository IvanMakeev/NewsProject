
package com.example.newsproject.data.repository.core

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.newsproject.data.repository.network.NetworkState
import com.example.newsproject.data.repository.util.PagingRequestHelper

/**
 * Data класс, который содержит в себе PagedList с данными, сетевое состояние и экземпляр класса хелпера
 */
data class SearchResult<T>(
    val data: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val helper: PagingRequestHelper
)
