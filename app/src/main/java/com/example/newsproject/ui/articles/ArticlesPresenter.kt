package com.example.newsproject.ui.articles

import com.example.newsproject.data.repository.NewsRepository

class ArticlesPresenter(
    private val repository: NewsRepository
) {

    private var view: ArticlesView? = null
    private val searchResult = repository.search()

    fun onAttach(view: ArticlesView) {
        this.view = view
    }

    fun loadPagedArticle() {
        view?.showArticles(result = searchResult)
    }

    fun retry() {
//        loadPagedArticle()
        searchResult.helper.retryAllFailed()
    }

    fun onDetach() {
        this.view = null
    }

    fun openWebView(url: String) {
        view?.openWebViewFragment(url)
    }
}