package com.nick.topbook.module.article.model

import com.google.gson.annotations.SerializedName

data class ArticleDetailResult(
	@SerializedName("data")
	val detail: Detail,
	val success: Boolean
)

data class Detail(
	val articleId: Int,
	val avatarUrl: String,
	val categoryId: Int,
	val content: String,
	val createTime: String,
	val description: Any,
	val like: Boolean,
	val likeTotal: Int,
	val nickname: String,
	val share: Int,
	val title: String,
	val updateTime: String,
	val upvote: Int,
	val userId: String
)