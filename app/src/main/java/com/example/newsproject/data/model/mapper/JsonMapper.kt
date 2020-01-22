package com.example.newsproject.data.model.mapper

import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.room.ArticleCache
import javax.inject.Inject

class JsonMapper @Inject constructor() :
    IMapper<Article, ArticleCache> {

    override fun mapFromJsonToRoom(type: Article): ArticleCache {
        return ArticleCache(
            0,
            type.title,
            type.description,
            type.url,
            type.urlToImage,
            type.publishedAt
        )
    }
}