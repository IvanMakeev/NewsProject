package com.example.newsproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.newsproject.R
import com.example.newsproject.data.repository.network.NetworkState
import com.example.newsproject.data.model.room.ArticleRoom

class PagedArticleAdapter(
    diffCallback: DiffUtil.ItemCallback<ArticleRoom>,
    private var itemClickListener: OnItemClickListener,
    private val retryCallback: () -> Unit
) : PagedListAdapter<ArticleRoom, BaseViewHolder<*>>(diffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            R.layout.item_article -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
                ArticleViewHolder(view, itemClickListener)
            }
            R.layout.item_network_state -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false)
                NetworkStateViewHolder(view, retryCallback)
            }
            else -> throw IllegalArgumentException(parent.context.getString(R.string.error_view_type))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val data: ArticleRoom? = getItem(position)
                data?.position = position
                holder.bind(data)
            }
            is NetworkStateViewHolder -> holder.bind(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_article
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    interface OnItemClickListener {
        fun onItemClick(url: String)
    }
}