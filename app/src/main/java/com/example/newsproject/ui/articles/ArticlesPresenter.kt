package com.example.newsproject.ui.articles

import com.example.newsproject.data.repository.NewsRepository
import com.example.newsproject.ui.articles.contract.ArticlesViewContract

class ArticlesPresenter(
    private val repository: NewsRepository
) {
    private var view: ArticlesViewContract? = null
    private val searchResult = repository.loadData()

    fun onAttach(v: ArticlesViewContract) {
        view = v
    }

    fun loadArticles() {
        view?.showArticles(result = searchResult)
    }

    fun retry() {
        searchResult.helper.retryAllFailed()
    }

    fun openWebView(url: String) {
        if (url.isEmpty()) {
            view?.showWebViewErrorToast()
            return
        }
        view?.openWebViewFragment(url)
    }

    fun onItemsRefresh() {
        repository.onItemsRefresh()
        view?.refreshIsDone(true)
    }

    fun onDetach() {
        view = null
    }
}