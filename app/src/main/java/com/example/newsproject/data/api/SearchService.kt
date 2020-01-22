package com.example.newsproject.data.api

import android.util.Log
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.json.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "GithubService"

fun searchRepos(
    api: NewsApi,
    query: String,
    date: String,
    sortBy: String,
    page: Int,
    onSuccess: (repos: List<Article>) -> Unit,
    onError: (error: String) -> Unit
) {
    Log.d(TAG, "query: $query, page: $page")
    api.getNews(
        query = query,
        date = date,
        sortBy = sortBy,
        page = page.toString()
    ).enqueue(
        object : Callback<News> {
            override fun onFailure(call: Call<News>?, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<News>?,
                response: Response<News>
            ) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    onSuccess(articles)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}