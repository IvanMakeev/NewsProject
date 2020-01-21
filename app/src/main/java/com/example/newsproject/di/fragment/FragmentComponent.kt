package com.example.newsproject.di.fragment

import com.example.newsproject.ui.articles.ArticlesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [PresenterModule::class])
interface FragmentComponent {

    fun inject(injector: ArticlesFragment)


    @Subcomponent.Builder
    interface Builder {
        fun build(): FragmentComponent
    }
}