package com.example.newsproject.data.repository.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsproject.data.repository.util.PagingRequestHelper

/**
 * Функция расширения, которая позволяет получать сетевые состояния
 */
fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val networkStateLiveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> networkStateLiveData.postValue(NetworkState.LOADING)
            report.hasError() -> networkStateLiveData.postValue(
                    NetworkState.error(getErrorMessage(report)))
            else -> networkStateLiveData.postValue(NetworkState.LOADED)
        }
    }
    return networkStateLiveData
}

private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}
