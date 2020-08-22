package com.nick.topbook.module.article.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R

class LoadMoreFooterAdapter : LoadStateAdapter<RecyclerView.ViewHolder>() {

	private var mRetry: (() -> Unit)? = null

	fun retry(retry: (() -> Unit)?) {
		mRetry = retry
	}

	var endOfPaginationReached = false
		set(value) {
			if (field != value) {
				if (value) {
					notifyItemInserted(0)
				} else {
					notifyItemRemoved(0)
				}
				field = value
			}
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
		val loadMoreFooterViewHolder = holder as LoadMoreFooterViewHolder
		loadMoreFooterViewHolder.loadMoreProgressBar.visibility = View.VISIBLE
		val context = loadMoreFooterViewHolder.context
		loadMoreFooterViewHolder.loadMoreText.apply {
			setOnClickListener {
				mRetry?.invoke()
			}
			isClickable = true
		}
		if (endOfPaginationReached) {
			loadMoreFooterViewHolder.loadMoreText.text = context.getString(R.string.foot_load_more_end)
			loadMoreFooterViewHolder.loadMoreText.isClickable = false
			loadMoreFooterViewHolder.loadMoreProgressBar.visibility = View.GONE
			return
		}
		when (loadState) {
			is LoadState.Loading -> {
				loadMoreFooterViewHolder.loadMoreText.text = context.getString(R.string.foot_load_more_loading)
				loadMoreFooterViewHolder.loadMoreText.isClickable = false
				loadMoreFooterViewHolder.loadMoreProgressBar.visibility = View.VISIBLE
			}
			is LoadState.Error -> {
				loadMoreFooterViewHolder.loadMoreText.text = context.getString(R.string.foot_load_more_retry)
				loadMoreFooterViewHolder.loadMoreText.isClickable = true
				loadMoreFooterViewHolder.loadMoreProgressBar.visibility = View.GONE
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): RecyclerView.ViewHolder {
		return LoadMoreFooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_footer_loadmore, parent, false), parent.context)
	}

	internal class LoadMoreFooterViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
		val loadMoreText: TextView = itemView.findViewById(R.id.tv_footer_load_more)
		val loadMoreProgressBar: ProgressBar = itemView.findViewById(R.id.pb_footer_load_more)
	}
}