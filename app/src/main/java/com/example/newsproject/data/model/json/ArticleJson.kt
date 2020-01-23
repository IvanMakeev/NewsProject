package com.example.newsproject.data.model.json

import com.google.gson.annotations.SerializedName


data class ArticleJson(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("urlToImage")
    var urlToImage: String? = null,
    @SerializedName("publishedAt")
    var publishedAt: String? = null
)