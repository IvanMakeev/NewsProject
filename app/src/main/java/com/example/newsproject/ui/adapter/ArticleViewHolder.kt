package com.example.newsproject.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.newsproject.R
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.data.model.room.ArticleCache
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ArticleViewHolder(
    itemView: View,
    private var itemClickListener: PagedArticleAdapter.OnItemClickListener
) : BaseViewHolder<ArticleCache>(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val newsImage: ImageView = itemView.findViewById(R.id.article_image)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val date: TextView = itemView.findViewById(R.id.date)
    private val progress: ProgressBar = itemView.findViewById(R.id.loading_image_progress)

    override fun bind(data: ArticleCache?) {
        data?.urlToImage?.let { loadImage(it) }
        title.text = data?.title
        description.text = data?.description
        date.text = data?.publishedAt

        if (data?.url.isNullOrEmpty()) {
            itemView.setOnClickListener { itemClickListener.onItemClick("") }
        } else {
            itemView.setOnClickListener { itemClickListener.onItemClick(data?.url!!) }
        }

        Log.d("TAG", data!!.publishedAt!!)
    }

    private fun loadImage(urlToImage: String) {
        if (urlToImage.trim().isEmpty()) {
            newsImage.setImageResource(R.drawable.error_placeholder_96dp)
            progress.visibility = View.GONE
        } else {
            Picasso.get()
                .load(urlToImage)
                .error(R.drawable.error_placeholder_96dp)
                .into(newsImage, object : Callback {
                    override fun onSuccess() {
                        progress.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progress.visibility = View.GONE
                    }

                })
        }
    }
}