package com.nick.topbook.module.article.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R

class RefreshHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	internal class RefreshHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return RefreshHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_header_refresh, parent, false))
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
	}

	override fun getItemCount() = 1
}