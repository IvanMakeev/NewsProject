package com.example.newsproject.data.database

import androidx.paging.DataSource
import androidx.room.*
import com.example.newsproject.data.model.room.ArticleRoom

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articleCaches: List<ArticleRoom>)

    @Query("select * from articles order by publishedAt desc")
    fun fetchArticles(): DataSource.Factory<Int, ArticleRoom>

    @Query("delete from articles")
    fun clearArticles()

}