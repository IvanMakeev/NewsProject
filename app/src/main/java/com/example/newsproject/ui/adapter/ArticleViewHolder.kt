package com.example.newsproject.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.newsproject.R
import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.utils.DateUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ArticleViewHolder(
    itemView: View,
    private var itemClickListener: PagedArticleAdapter.OnItemClickListener
) : BaseViewHolder<ArticleRoom>(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val newsImage: ImageView = itemView.findViewById(R.id.article_image)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val date: TextView = itemView.findViewById(R.id.published_article_date)
    private val progress: ProgressBar = itemView.findViewById(R.id.loading_image_progress)
    private val position: TextView = itemView.findViewById(R.id.article_position)

    override fun bind(data: ArticleRoom?) {
        data?.let { safeData ->
            safeData.urlToImage?.let { url ->
                loadImage(url)
            }
            title.text = safeData.title
            description.text = safeData.description
            position.text = (safeData.position + 1).toString()
            safeData.publishedAt?.let {
                setDate(it)
            }

            if (safeData.url.isNullOrEmpty()) {
                setClickListener("")
            } else {
                setClickListener(safeData.url!!)
            }
        }
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

    private fun setDate(it: String) {
        date.text = DateUtils.format(it)
    }

    private fun setClickListener(url: String) {
        itemView.setOnClickListener { itemClickListener.onItemClick(url) }
    }
}