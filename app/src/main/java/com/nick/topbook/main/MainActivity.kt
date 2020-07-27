package com.nick.topbook.main

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nick.topbook.R
import com.nick.topbook.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

	override fun initView(): View {
		supportActionBar?.hide()
		return View.inflate(this, R.layout.activity_main, null)
	}

	override fun onSupportNavigateUp(): Boolean {
		return findNavController(R.id.fcv_main_center).navigateUp()
	}

	override fun initData() {
		bnv_main_tab_bottom.setupWithNavController(findNavController(R.id.fcv_main_center))
	}
}
