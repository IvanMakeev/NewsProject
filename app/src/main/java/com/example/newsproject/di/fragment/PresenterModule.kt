package com.example.newsproject.di.fragment

import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.database.NewsDao
import com.example.newsproject.ui.articles.ArticlesPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @FragmentScope
    fun providePArticlesPresenter(api: NewsApi, dao: NewsDao) = ArticlesPresenter(api, dao)
}