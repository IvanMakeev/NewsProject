package com.example.newsproject.di.app

import com.example.newsproject.di.fragment.FragmentComponent
import com.example.newsproject.ui.articles.ArticlesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun fragmentComponentBuilder(): FragmentComponent.Builder
}