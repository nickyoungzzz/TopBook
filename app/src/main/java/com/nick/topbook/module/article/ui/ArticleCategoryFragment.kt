package com.nick.topbook.module.article.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.nick.topbook.R
import com.nick.topbook.base.BaseFragment
import com.nick.topbook.data.observeResource
import com.nick.topbook.module.article.model.Category
import com.nick.topbook.module.article.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleCategoryFragment : BaseFragment() {

	private val articleViewModel by viewModels<ArticleViewModel>()

	private lateinit var adapter: FragmentPagerAdapter

	override fun initView(): View {
		return View.inflate(this.context, R.layout.fragment_article, null)
	}

	override fun initData(savedInstanceState: Bundle?) {
		val titleList = arrayListOf<Category>()
		articleViewModel.getArticleCategory(0, 20).observeResource<List<Category>>(viewLifecycleOwner) {
			succeed {
				titleList.clear()
				titleList.addAll(it)
				adapter.notifyDataSetChanged()
			}
			loading(Toast.makeText(this@ArticleCategoryFragment.activity, "loading", Toast.LENGTH_SHORT)::show)
			lost { Toast.makeText(this@ArticleCategoryFragment.activity, it?.errMsg, Toast.LENGTH_SHORT).show() }
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