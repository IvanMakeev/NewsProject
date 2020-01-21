package com.example.newsproject.di.app

import androidx.room.Room
import com.example.newsproject.AppDelegate
import com.example.newsproject.data.database.NewsDao
import com.example.newsproject.data.database.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appDelegate: AppDelegate) {

    @Provides
    @Singleton
    fun provideApp() = appDelegate

    @Provides
    @Singleton
    fun provideDatabase(): NewsDatabase {
        return Room.databaseBuilder(
            appDelegate,
            NewsDatabase::class.java,
            "news_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBehanceDao(database: NewsDatabase): NewsDao {
        return database.newsDao
    }
}