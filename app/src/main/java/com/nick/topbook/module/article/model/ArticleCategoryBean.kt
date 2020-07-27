package com.nick.topbook.module.article.model

import com.google.gson.annotations.SerializedName

data class ArticleCategoryResult(
	val success: Boolean,
	@SerializedName("data")
	val categoryData: CategoryData,
)

data class CategoryData(
	val limit: Int,
	val start: Int,
	val total: Int,
	@SerializedName("items")
	val categories: List<Category>,
)

data class Category(
	val categoryId: Int,
	val createTime: String,
	val name: String,
	val priority: Int,
	val updateTime: String
)