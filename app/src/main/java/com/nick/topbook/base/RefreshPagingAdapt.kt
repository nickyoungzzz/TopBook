package com.nick.topbook.base

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

class RefreshPagingAdapt<T : Any, VH : RecyclerView.ViewHolder>(private val pagingDataAdapter: PagingDataAdapter<T, VH>) {

	private var headerAdapter: RefreshHeaderAdapter = RefreshHeaderAdapter()
	private var footerAdapter: LoadMoreFooterAdapter = LoadMoreFooterAdapter()
	private var isRefreshing = false
	private var isAppending = false
	private var mLoadErrorListener: ((Throwable) -> Unit)? = null

	fun registerRetryListener(retry: () -> Unit = {}) {
		footerAdapter.retry {
			retry.invoke()
			pagingDataAdapter.retry()
		}
	}

	fun refresh(refresh: () -> Unit = {}) {
		refresh.invoke()
		pagingDataAdapter.refresh()
	}

	fun registerErrorListener(loadErrorListener: ((Throwable) -> Unit)) {
		mLoadErrorListener = loadErrorListener
	}

	fun toAdapter(): ConcatAdapter {
		pagingDataAdapter.addLoadStateListener {
			val refresh = it.refresh
			val append = it.append
			val isRefresh = append is LoadState.NotLoading && refresh is LoadState.Loading
			val isAppend = append is LoadState.Loading && refresh is LoadState.NotLoading
			val isRefreshEnd = isRefreshing && refresh is LoadState.NotLoading
			val isAppendEnd = isAppending && append is LoadState.NotLoading
			when {
				isRefresh -> headerAdapter.notifyItemInserted(0)
				isAppend || isAppendEnd -> footerAdapter.endOfPaginationReached = append.endOfPaginationReached
				isRefreshEnd -> headerAdapter.notifyItemRemoved(0)
				refresh is LoadState.Error -> mLoadErrorListener?.invoke(refresh.error)
				append is LoadState.Error -> mLoadErrorListener?.invoke(append.error)
			}
			isRefreshing = isRefresh
			isAppending = isAppend
		}
		return ConcatAdapter(headerAdapter, pagingDataAdapter.withLoadStateFooter(footerAdapter))
	}
}