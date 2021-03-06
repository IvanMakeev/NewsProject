package com.example.newsproject.data.api

import com.example.newsproject.data.model.json.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    fun getNews(
        @Query("q") query: String,
        @Query("from") date: String,
        @Query("sortBy") sortBy: String,
        @Query("page") page: String
    ): Call<News>
}