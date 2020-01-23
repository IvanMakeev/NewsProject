package com.example.newsproject.di.fragment

import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsDao
import com.example.newsproject.data.database.NewsLocalCache
import com.example.newsproject.data.model.json.ArticleJson
import com.example.newsproject.data.model.mapper.JsonMapper
import com.example.newsproject.data.model.mapper.IMapper
import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.data.repository.NewsRepository
import com.example.newsproject.ui.articles.ArticlesPresenter
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class PresenterModule {

    @Provides
    @FragmentScope
    fun provideArticlesPresenter(repository: NewsRepository) = ArticlesPresenter(repository)

    @Provides
    @FragmentScope
    fun provideRepository(newsApi: NewsApi, cache: NewsLocalCache, executor: Executor) =
        NewsRepository(newsApi = newsApi, cache = cache, ioExecutor = executor)

    @Provides
    @FragmentScope
    fun provideNewsLocalCache(
        newsDao: NewsDao,
        executor: Executor,
        jsonMapper: IMapper<ArticleJson, ArticleRoom>
    ): NewsLocalCache {
        return NewsLocalCache(newsDao = newsDao, ioExecutor = executor, mapper = jsonMapper)
    }

    @Provides
    @FragmentScope
    fun provideJsonMapper(mapper: JsonMapper): IMapper<ArticleJson, ArticleRoom> {
        return mapper
    }

    @Provides
    @FragmentScope
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

}