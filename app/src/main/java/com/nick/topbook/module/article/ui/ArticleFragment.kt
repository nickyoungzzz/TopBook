package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_category.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private lateinit var articleAdapter: ArticlePagedAdapter

	private val footerAdapter = LoadMoreFooterAdapter()

	override fun initView(): View = View.inflate(context, R.layout.fragment_article_category, null)

	override fun initData(savedInstanceState: Bundle?) {
		val categoryId = arguments?.getInt("categoryId") ?: 0
		articleAdapter = ArticlePagedAdapter()
		rv_article.apply {
			layoutManager = LinearLayoutManager(context)
			itemAnimator = DefaultItemAnimator()
			adapter = articleAdapter.withLoadStateFooter(footerAdapter)
		}
		registerListener()
		pullData(categoryId)
	}

	private fun registerListener() {
		article_refresh.setOnRefreshListener {
			articleAdapter.refresh()
		}
		rv_article.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				articleAdapter.isScrolling = newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
			}
		})
		footerAdapter.retry {
			articleAdapter.retry()
		}
		articleAdapter.addLoadStateListener {
			println(it.toString())
			val refresh = it.refresh
			val append = it.append
			if (refresh is LoadState.Error) {
				Toast.makeText(context, refresh.error.message, Toast.LENGTH_SHORT).show()
			} else if (append is LoadState.Error) {
				Toast.makeText(context, append.error.message, Toast.LENGTH_SHORT).show()
			}
			footerAdapter.endOfPaginationReached = append.endOfPaginationReached && !refresh.endOfPaginationReached
		}
	}

	private fun pullData(categoryId: Int) {
		lifecycleScope.launch {
			articleViewModel.getArticlePagedList(0, 50, categoryId).collectLatest {
				article_refresh.isRefreshing = false
				articleAdapter.submitData(lifecycle, it)
			}
		}
	}
}