package com.example.newsproject.ui.articles.contract

import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.data.repository.core.SearchResult

interface ArticlesViewContract {

    fun showArticles(result: SearchResult<ArticleRoom>)

    fun openWebViewFragment(url:String)

    fun showWebViewErrorToast()
}