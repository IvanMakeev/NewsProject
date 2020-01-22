package com.example.newsproject.di.fragment

import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsDao
import com.example.newsproject.data.database.NewsLocalCache
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.mapper.JsonMapper
import com.example.newsproject.data.model.mapper.IMapper
import com.example.newsproject.data.model.room.ArticleCache
import com.example.newsproject.data.repository.NewsRepository
import com.example.newsproject.ui.articles.ArticlesPresenter
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors

@Module
class PresenterModule {

    @Provides
    @FragmentScope
    fun provideArticlesPresenter(repository: NewsRepository) = ArticlesPresenter(repository)

    @Provides
    @FragmentScope
    fun provideRepository(newsApi: NewsApi, cache: NewsLocalCache) = NewsRepository(newsApi, cache)

    @Provides
    @FragmentScope
    fun provideNewsLocalCache(newsDao: NewsDao, jsonMapper: IMapper<Article, ArticleCache>): NewsLocalCache {
        return NewsLocalCache(newsDao, Executors.newSingleThreadExecutor(), jsonMapper)
    }

    @Provides
    @FragmentScope
    fun provideJsonMapper(mapper: JsonMapper): IMapper<Article, ArticleCache> {
        return mapper
    }
}