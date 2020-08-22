package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.module.article.model.Category
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleCategoryFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private lateinit var adapter: FragmentPagerAdapter

	override fun initView(): View {
		return View.inflate(this.context, R.layout.fragment_article, null)
	}

	override fun initData(savedInstanceState: Bundle?) {
		val titleList = arrayListOf<Category>()
		lifecycleScope.launch {
			articleViewModel.getArticleCategory(0, 20).catch {
				Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
			}.collectLatest {
				titleList.clear()
				titleList.addAll(it)
				adapter.notifyDataSetChanged()
			}
		}
		adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
			override fun getItem(position: Int): Fragment {
				return childFragmentManager.apply { fragmentFactory = FragmentFactory() }.fragmentFactory
					.instantiate(javaClass.classLoader!!, ArticleFragment::class.qualifiedName!!)
					.apply { arguments = bundleOf("categoryId" to titleList[position].categoryId) }
			}

			override fun getCount(): Int {
				return titleList.size
			}

			override fun getPageTitle(position: Int): CharSequence? {
				return titleList[position].name
			}
		}
		vp_article.adapter = adapter
		vp_article.offscreenPageLimit = 10
		vp_article.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl_article))
		tl_article.setupWithViewPager(vp_article)
	}

}