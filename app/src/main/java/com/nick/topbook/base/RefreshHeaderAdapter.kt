package com.nick.topbook.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.nick.topbook.R

class RefreshHeaderAdapter(@LayoutRes val layoutId: Int = R.layout.layout_header_refresh) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	internal class RefreshHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return RefreshHeaderViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
	}

	override fun getItemCount() = 0
}