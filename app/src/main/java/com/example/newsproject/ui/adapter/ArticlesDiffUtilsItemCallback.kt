package com.example.newsproject.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsproject.data.model.room.ArticleRoom

class ArticlesDiffUtilsItemCallback : DiffUtil.ItemCallback<ArticleRoom>() {

    override fun areItemsTheSame(oldItem: ArticleRoom, newItem: ArticleRoom): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(oldItem: ArticleRoom, newItem: ArticleRoom): Boolean {
        return oldItem == newItem
    }
}
