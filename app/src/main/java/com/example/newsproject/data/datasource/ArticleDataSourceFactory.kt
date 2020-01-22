package com.example.newsproject.data.datasource
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.DataSource
//import com.example.newsproject.data.api.NewsApi
//import com.example.newsproject.data.model.json.Article
//import io.reactivex.disposables.CompositeDisposable
//
//class ArticleDataSourceFactory(
//    private val api: NewsApi,
//    private val compositeDisposable: CompositeDisposable
//) : DataSource.Factory<Int, Article>() {
//
//    private var _articleDataSourceLiveData = MutableLiveData<ArticleDataSource>()
//    var articleDataSourceLiveData: LiveData<ArticleDataSource> = _articleDataSourceLiveData
//
//    override fun create(): DataSource<Int, Article> {
//        val articleDataSource = ArticleDataSource(api, compositeDisposable)
//        _articleDataSourceLiveData.postValue(articleDataSource)
//        return articleDataSource
//    }
//
//}