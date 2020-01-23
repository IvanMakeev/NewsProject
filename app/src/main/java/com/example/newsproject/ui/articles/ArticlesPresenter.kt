package com.example.newsproject.ui.articles

import com.example.newsproject.data.repository.NewsRepository
import com.example.newsproject.ui.articles.contract.ArticlesViewContract

class ArticlesPresenter(
    repository: NewsRepository
) {
    private var view: ArticlesViewContract? = null
    private val searchResult = repository.search()

    fun onAttach(view: ArticlesViewContract) {
        this.view = view
    }

    fun loadPagedArticle() {
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

    fun onDetach() {
        this.view = null
    }
}