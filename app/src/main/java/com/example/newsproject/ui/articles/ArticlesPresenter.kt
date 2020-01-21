package com.example.newsproject.ui.articles

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.datasource.ArticleDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.example.newsproject.data.database.NewsDao
import com.example.newsproject.data.datasource.ArticleDataSourceFactory
import com.example.newsproject.data.datasource.NetworkState
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.mapper.JsonMapper
import io.reactivex.Observable
import io.reactivex.Single

class ArticlesPresenter(
    private var api: NewsApi,
    private var dao: NewsDao
) {

    private val compositeDisposable = CompositeDisposable()
    private var view: ArticlesView? = null
    private var sourceFactory: ArticleDataSourceFactory? = null
    private var config: PagedList.Config? = null
    private var mapper: JsonMapper = JsonMapper()
    private var articles: PagedList<Article>? = null
    private var isNewLoad = false
    //    var newsList: LiveData<PagedList<Article>>
//    private var dataSource: DataSource<Int, Article>? = null


    fun onAttach(view: ArticlesView) {
        this.view = view
//        isNewLoad = false
    }

    init {
        sourceFactory = ArticleDataSourceFactory(api, compositeDisposable)
//        dataSource = sourceFactory!!.create()

        config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(1)
            .setPageSize(5)
            .build()
//        newsList = LivePagedListBuilder(sourceFactory!!, config!!).build()

    }


    fun loadPagedArticle() {
        if (articles.isNullOrEmpty()) {
            downloadData()
        } else {
//            dataSource!!.invalidate()
//            sourceFactory!!.articleDataSourceLiveData.value!!.invalidate()
            view?.showArticles(articles!!)
        }
    }

    private fun downloadData() {
        val pagedList = RxPagedListBuilder(sourceFactory!!, config!!)
            .buildObservable()
        compositeDisposable.add(
            pagedList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ articles ->
                    articles?.let { it ->
                        saveData(it)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
    }

    private fun saveData(articles: PagedList<Article>) {
        this.articles = articles
        view?.showArticles(articles)
    }

    fun retry() {
        sourceFactory!!.articleDataSourceLiveData.value!!.retry()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<ArticleDataSource, NetworkState>(
        sourceFactory!!.articleDataSourceLiveData
    ) {
        it.networkState
    }

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<ArticleDataSource, NetworkState>(
        sourceFactory!!.articleDataSourceLiveData
    ) { it.initialLoad }

    fun disposeAll() {
        compositeDisposable.dispose()
    }

    fun onDetach() {
        this.view = null
        isNewLoad = true
    }

    fun openWebView(url: String) {
        view?.openWebViewFragment(url)
    }
}