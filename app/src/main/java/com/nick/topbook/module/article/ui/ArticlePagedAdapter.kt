package com.nick.topbook.module.article.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nick.topbook.R
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.util.format1
import com.nick.topbook.util.format2
import com.nick.topbook.util.parseDateTime2NewFormat

class ArticlePagedAdapter : PagingDataAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffItemCallBack()) {

	var isScrolling = false
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article_category_fragment, parent, false))
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val articleViewHolder = holder as ArticleViewHolder
		articleViewHolder.icon.setImageResource(R.drawable.ic_launcher_foreground)
		getItem(position)?.let {
			articleViewHolder.icon.tag = it.cover
			if (!isScrolling) {
				articleViewHolder.icon.load(it.cover) {
					placeholder(R.drawable.ic_launcher_foreground)
					allowRgb565(true)
					target(onSuccess = { it1 ->
						if (articleViewHolder.icon.tag == it.cover) {
							articleViewHolder.icon.setImageDrawable(it1)
						}
					}, onError = {
						articleViewHolder.icon.setImageResource(R.drawable.ic_launcher_foreground)
					})
				}
			}
			articleViewHolder.title.text = it.title
			articleViewHolder.createDate.text = parseDateTime2NewFormat(it.createTime, format1, format2)
			articleViewHolder.likeNum.text = "${it.likeTotal}"
			articleViewHolder.like.setImageResource(if (it.like) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
		}
	}

	internal class ArticleDiffItemCallBack : DiffUtil.ItemCallback<Article>() {

		override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
			return oldItem.articleId == newItem.articleId
		}

		override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
			return oldItem.title == newItem.title
		}
	}

	internal class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val icon: ImageView = itemView.findViewById(R.id.article_icon)
		val title: TextView = itemView.findViewById(R.id.article_title)
		val createDate: TextView = itemView.findViewById(R.id.article_createDate)
		val likeNum: TextView = itemView.findViewById(R.id.article_likeNum)
		val like: ImageView = itemView.findViewById(R.id.article_like)
	}

}