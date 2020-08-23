package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.base.RefreshPagingAdapt
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_category.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private val articleAdapter = ArticlePagedAdapter()
	private val refreshPagingAdapt: RefreshPagingAdapt<Article, RecyclerView.ViewHolder> = RefreshPagingAdapt(articleAdapter)

	override fun initView(): View = View.inflate(context, R.layout.fragment_article_category, null)

	override fun initData(savedInstanceState: Bundle?) {
		val categoryId = arguments?.getInt("categoryId") ?: 0
		rv_article.apply {
			layoutManager = LinearLayoutManager(context)
			itemAnimator = DefaultItemAnimator()
			adapter = refreshPagingAdapt.toAdapter()
		}
		registerListener()
		pullData(categoryId)
	}

	private fun registerListener() {
		rv_article.observeScrolling {
			articleAdapter.isScrolling = it
		}
		rv_article.observeRefreshing {
			refreshPagingAdapt.refresh()
		}
		refreshPagingAdapt.registerRetryListener {
			Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
		}
		refreshPagingAdapt.addStateListener {
			Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
		}
	}

	private fun pullData(categoryId: Int) {
		lifecycleScope.launch {
			articleViewModel.getArticlePagedList(0, 50, categoryId).collectLatest {
				articleAdapter.submitData(lifecycle, it)
				rv_article.iSRefreshing = false
			}
		}
	}
}