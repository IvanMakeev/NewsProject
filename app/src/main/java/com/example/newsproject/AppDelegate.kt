package com.example.newsproject

import android.app.Application
import com.example.newsproject.di.app.AppComponent
import com.example.newsproject.di.app.AppModule
import com.example.newsproject.di.app.DaggerAppComponent
import com.example.newsproject.di.fragment.FragmentComponent

class AppDelegate : Application() {

    companion object {
        private lateinit var INSTANCE: AppDelegate
        @JvmStatic
        fun getInjector(): AppDelegate = INSTANCE
    }

    private lateinit var appComponent: AppComponent
    private var fragmentComponent: FragmentComponent? = null
//    private var viewComponent: ViewComponent? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent() = appComponent

    fun buildFragmentComponent(): FragmentComponent {

        if (fragmentComponent == null) {
            fragmentComponent = appComponent.fragmentComponentBuilder().build()
        }
        return fragmentComponent as FragmentComponent
    }

    fun clearFragmentComponent() {
        fragmentComponent = null
    }
//
//    fun plusViewComponent(articlesFragment: ArticlesFragment): ViewComponent {
//        if (viewComponent == null) {
//            viewComponent = buildFragmentComponent()
//                .viewComponentBuilder()
//                .viewModule(ViewModule(articlesFragment))
//                .build()
//        }
//        return viewComponent as ViewComponent
//    }
//
//    fun clearViewComponent() {
//        viewComponent = null
//    }
//
//    fun getInjectorView() = viewComponent
}
