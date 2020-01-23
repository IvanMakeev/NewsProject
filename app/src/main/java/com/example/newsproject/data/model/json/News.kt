package com.example.newsproject.data.model.json

import com.google.gson.annotations.SerializedName


data class News(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = null,
    @SerializedName("articles")
    var articles: List<ArticleJson>? = null
)