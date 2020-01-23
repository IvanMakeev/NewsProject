package com.example.newsproject.data.repository.network

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

/**
 * Data класс для работы с сетевыми состояниями
 */
@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val message: String? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(
            Status.FAILED, msg
        )
    }
}
