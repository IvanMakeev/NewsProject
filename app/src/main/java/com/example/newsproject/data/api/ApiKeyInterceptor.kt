package com.example.newsproject.data.api

import com.example.newsproject.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor : Interceptor {

    companion object {
        private const val PROPERTY_API_KEY = "apiKey"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request
            .url
            .newBuilder()
            .addQueryParameter(PROPERTY_API_KEY, BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            request
                .newBuilder()
                .url(httpUrl)
                .build()
        )
    }
}