package com.example.newsproject.data.repository.core

import com.example.newsproject.data.model.room.ArticleRoom

/**
 * Базовый интрефейс для репозитория
 */
interface BaseRepository {
    fun loadData() : SearchResult<ArticleRoom>

    fun onItemsRefresh()
}