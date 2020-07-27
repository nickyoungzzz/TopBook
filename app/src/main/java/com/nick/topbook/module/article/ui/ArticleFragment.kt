package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.data.observeResource
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.model.ArticleDetailResult
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_category.*

class ArticleFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private lateinit var articleAdapter: ArticlePagedAdapter

	override fun initView(): View = View.inflate(context, R.layout.fragment_article_category, null)

	override fun initData(savedInstanceState: Bundle?) {
		val categoryId = arguments?.getInt("categoryId") ?: 0
		articleAdapter = ArticlePagedAdapter()
		rv_article.apply {
			layoutManager = LinearLayoutManager(context)
			itemAnimator = DefaultItemAnimator()
			adapter = articleAdapter
		}
		registerListener(categoryId)
		pullData(categoryId)
	}

	private fun registerListener(categoryId: Int) {
		article_refresh.setOnRefreshListener {
			pullData(categoryId)
		}
		rv_article.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				articleAdapter.isScrolling = newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
			}
		})
		articleViewModel.getArticleDetail().observeResource<ArticleDetailResult>(this) {
			succeed { it.detail }
		}
	}

	private fun pullData(categoryId: Int) {
		articleViewModel.getArticlePagedList(0, 10, categoryId).observeResource<PagedList<Article>>(this) {
			article_refresh.isRefreshing = false
			succeed {
				articleAdapter.submitList(it)
			}
			lost {
				Toast.makeText(context, it?.errMsg, Toast.LENGTH_SHORT).show()
			}
		}
	}
}