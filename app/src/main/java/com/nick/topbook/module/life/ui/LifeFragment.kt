package com.nick.topbook.module.life.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.topbook.R
import com.nick.topbook.module.life.model.LifeTopic
import com.nick.topbook.module.life.viewmodel.LifeViewModel
import kotlinx.android.synthetic.main.fragment_life.*

class LifeFragment : Fragment() {

	private val lifeViewModel by viewModels<LifeViewModel>()

	private lateinit var lifeAdapter: LifeAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		observeLiveData()
		lifeAdapter = LifeAdapter()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_life, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		rv_life.layoutManager = LinearLayoutManager(context)
		rv_life.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
		rv_life.adapter = lifeAdapter
	}

	private fun initView() {
	}

	private fun observeLiveData() {
		lifeViewModel.lifeTopics.observe(this, { t: List<LifeTopic>? ->
			t?.let {
				lifeAdapter.updateDataList(t)
			}
		})
	}
}