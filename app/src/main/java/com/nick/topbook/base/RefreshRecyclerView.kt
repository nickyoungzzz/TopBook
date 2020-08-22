package com.nick.topbook.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class RefreshRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

	private var mScroll: ((Boolean) -> Unit)? = null
	private var mRefresh: (() -> Unit)? = null
	var iSRefreshing = false

	fun observeScrolling(scroll: ((Boolean) -> Unit)?) {
		mScroll = scroll
	}

	fun observeRefreshing(refresh: (() -> Unit)?) {
		mRefresh = refresh
	}

	init {
		addOnScrollListener(object : OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				mScroll?.invoke(newState != SCROLL_STATE_IDLE && recyclerView.canScrollVertically(-1) && recyclerView.canScrollVertically(1))
			}
		})
		onFlingListener = object : OnFlingListener() {
			override fun onFling(velocityX: Int, velocityY: Int): Boolean {
				if (velocityY < -50 && !canScrollVertically(-1) && !iSRefreshing) {
					iSRefreshing = true
					mRefresh?.invoke()
				}
				return false
			}
		}
	}
}