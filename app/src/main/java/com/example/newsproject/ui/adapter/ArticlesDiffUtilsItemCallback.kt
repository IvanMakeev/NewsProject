package com.example.newsproject.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsproject.data.model.json.Article

class ArticlesDiffUtilsItemCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
