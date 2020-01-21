package com.example.newsproject.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.newsproject.data.api.NewsApi
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.utils.DateUtils
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ArticleDataSource(
    private val api: NewsApi,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    companion object {
        const val FIRST_PAGE = 1
        const val TOTAL_PAGE = 5
    }

    private var _networkState = MutableLiveData<NetworkState>()
    private var _initialLoad = MutableLiveData<NetworkState>()
    private var retryCompletable: Completable? = null

    var networkState: LiveData<NetworkState> = _networkState
    var initialLoad: LiveData<NetworkState> = _initialLoad


    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { })
            )
        }
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        _networkState.postValue(NetworkState.LOADING)
        _initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(api.getNews("android", "2019-04-00", "publishedAt", "1")
            .map { news ->
                news.articles!!.forEach { article ->
                    article.publishedAt = DateUtils.format(article.publishedAt!!)
                }
                news.articles!!
            }
            .subscribe({ article ->
                setRetry(null)
                _networkState.postValue(NetworkState.LOADED)
                _initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(article, null, next(FIRST_PAGE))
            },
                { throwable ->
                    setRetry(Action { loadInitial(params, callback) })
                    val error = NetworkState.error(throwable.message)
                    _networkState.postValue(error)
                    _initialLoad.postValue(error)
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(api.getNews("android", "2019-04-00", "publishedAt", params.key.toString())
            .map { news ->
                news.articles!!.forEach { article ->
                    article.publishedAt = DateUtils.format(article.publishedAt!!)
                }
                news.articles!!
            }
            .subscribe({ article ->
                setRetry(null)
                _networkState.postValue(NetworkState.LOADED)
                callback.onResult(article, next(params.key))
            },
                { throwable ->
                    setRetry(Action { loadAfter(params, callback) })
                    _networkState.postValue(NetworkState.error(throwable.message))
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
//        compositeDisposable.add(api.getNews("android", "2019-04-00", "publishedAt", params.key.toString(), "5")
//            .map { news ->
//                news.articles!!.forEach { article ->
//                    article.publishedAt = DateUtils.format(article.publishedAt!!)
//                }
//                news.articles!!
//            }
//            .subscribe({ article ->
//                setRetry(null)
//                callback.onResult(article, previous(params.key))
//            },
//                { throwable ->
//                    setRetry(Action { loadBefore(params, callback) })
//                    throwable.printStackTrace()
//                })
//        )
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    private fun next(pageNumber: Int): Int? {
        return if (pageNumber >= TOTAL_PAGE) {
            null
        } else {
            pageNumber.inc()
        }
    }

//    private fun previous(pageNumber: Int): Int? {
//        return if (pageNumber < FIRST_PAGE) {
//            null
//        } else {
//            pageNumber.dec()
//        }
//    }
}