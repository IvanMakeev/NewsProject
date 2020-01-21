package com.example.newsproject.ui.articles

import androidx.paging.PagedList
import com.example.newsproject.data.model.json.Article

interface ArticlesView {

    fun showArticles(articles: PagedList<Article>)

    fun openWebViewFragment(url:String)
}