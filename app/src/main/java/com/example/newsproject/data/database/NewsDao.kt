package com.example.newsproject.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsproject.data.model.room.ArticleCache

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleCaches: List<ArticleCache>)

    @Query("select * from articles")
    fun getArticles(): DataSource.Factory<Int, ArticleCache>
}