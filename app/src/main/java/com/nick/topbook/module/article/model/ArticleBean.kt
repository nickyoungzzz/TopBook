package com.nick.topbook.module.article.model

import com.google.gson.annotations.SerializedName

data class ArticleResult(
	val success: Boolean,
	@SerializedName("data")
	val articleData: ArticleData,
)

data class ArticleData(
	val limit: Int,
	val start: Int,
	val total: Int,
	@SerializedName("items")
	val articles: List<Article>,
)

data class Article(
	val articleId: Int,
	val categoryId: Int,
	val cover: String,
	val createTime: String,
	val description: Any,
	val like: Boolean,
	val likeTotal: Int,
	val title: String,
	val userId: String
)