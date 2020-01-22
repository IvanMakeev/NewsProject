package com.example.newsproject.ui.articles

import androidx.paging.PagedList
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.repository.ArticleSearchResult

interface ArticlesView {

    fun showArticles(result: ArticleSearchResult)

    fun openWebViewFragment(url:String)
}