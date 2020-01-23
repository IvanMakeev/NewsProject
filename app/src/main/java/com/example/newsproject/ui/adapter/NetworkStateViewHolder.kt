package com.example.newsproject.ui.adapter

import android.view.View
import com.example.newsproject.data.repository.network.NetworkState
import com.example.newsproject.data.repository.network.Status
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(view: View, private val retryCallback: () -> Unit) :
    BaseViewHolder<NetworkState>(view) {

    init {
        itemView.retry_loading_button.setOnClickListener { retryCallback() }
    }

    override fun bind(data: NetworkState?) {
        //error message
        itemView.error_message.visibility = if (data?.message != null) View.VISIBLE else View.GONE
        data?.let {
            itemView.error_message.text = it.message
        }

        //loading and retry
        itemView.retry_loading_button.visibility = if (data?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loading_progress_bar.visibility = if (data?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }
}