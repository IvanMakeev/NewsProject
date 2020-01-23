package com.example.newsproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsproject.data.model.room.ArticleRoom

@Database(entities = [ArticleRoom::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}