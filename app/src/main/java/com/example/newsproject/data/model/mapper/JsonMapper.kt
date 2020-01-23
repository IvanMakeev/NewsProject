package com.example.newsproject.data.model.mapper

import com.example.newsproject.data.model.json.ArticleJson
import com.example.newsproject.data.model.room.ArticleRoom
import javax.inject.Inject

/**
 * Маппер для конвертации Json в Room
 */
class JsonMapper @Inject constructor() : IMapper<ArticleJson, ArticleRoom> {

    override fun mapFromJsonToRoom(type: ArticleJson): ArticleRoom {
        return ArticleRoom(
            0,
            type.title,
            type.description,
            type.url,
            type.urlToImage,
            type.publishedAt
        )
    }
}