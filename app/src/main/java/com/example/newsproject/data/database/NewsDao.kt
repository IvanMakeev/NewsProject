package com.example.newsproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsproject.data.model.room.ArticleCache

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleCache: ArticleCache)

    @Query("select * from ArticleCache")
    fun getAll(): List<ArticleCache>
}