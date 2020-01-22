package com.example.newsproject.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.room.ArticleCache

class ArticlesDiffUtilsItemCallback : DiffUtil.ItemCallback<ArticleCache>() {

    override fun areItemsTheSame(oldItem: ArticleCache, newItem: ArticleCache): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(oldItem: ArticleCache, newItem: ArticleCache): Boolean {
        return oldItem == newItem
    }
}
