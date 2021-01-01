package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.data.Resource
import com.nick.topbook.module.article.model.Category
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleCategoryFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private lateinit var adapter: FragmentStateAdapter

	override fun initView(): View {
		return View.inflate(this.context, R.layout.fragment_article, null)
	}

	override fun initData(savedInstanceState: Bundle?) {
		val titleList = arrayListOf<Category>()
		lifecycleScope.launch {
			articleViewModel.getArticleCategory(0, 20).collectLatest {
				when (it) {
					is Resource.RespSuccess -> {
						titleList.addAll(it.data ?: emptyList())
						adapter.notifyDataSetChanged()
					}
					is Resource.RespError -> Toast.makeText(context, it.apiError?.errMsg, Toast.LENGTH_SHORT).show()
					is Resource.RespLoading -> {
					}
				}
			}
		}
		adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
			override fun getItemCount(): Int {
				return titleList.size
			}

			override fun createFragment(position: Int): Fragment {
				return childFragmentManager.apply { fragmentFactory = FragmentFactory() }.fragmentFactory
					.instantiate(javaClass.classLoader!!, ArticleFragment::class.qualifiedName!!)
					.apply { arguments = bundleOf("categoryId" to titleList[position].categoryId) }
			}
		}
		vp_article.adapter = adapter
		vp_article.offscreenPageLimit = 10
		TabLayoutMediator(tl_article, vp_article) { tab, position ->
			tab.text = titleList[position].name
		}.attach()
	}

}