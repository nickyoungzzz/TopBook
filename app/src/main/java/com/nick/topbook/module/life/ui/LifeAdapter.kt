package com.nick.topbook.module.life.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R
import com.nick.topbook.module.life.model.LifeTopic

class LifeAdapter(private val dataList: ArrayList<LifeTopic> = arrayListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	fun updateDataList(data: List<LifeTopic>) {
		dataList.clear()
		dataList.addAll(data)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return LifeViewHolder(View.inflate(parent.context, R.layout.item_article_category_fragment, null))
	}

	override fun getItemCount(): Int {
		return dataList.size
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val lifeViewHolder = holder as LifeViewHolder
	}

	internal class LifeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	}
}