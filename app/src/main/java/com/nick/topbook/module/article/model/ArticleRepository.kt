package com.nick.topbook.module.article.model

import com.google.gson.JsonParser
import com.nick.easyhttp.core.httpGet
import com.nick.topbook.constant.UrlConstant
import com.nick.topbook.data.ApiErrorResult
import com.nick.topbook.data.toEntity
import com.nick.topbook.data.transform2ApiError

class ArticleRepository {

	fun getArticleCategory(start: Int, limit: Int): ApiErrorResult<ArticleCategoryResult> {
		return httpGet {
			url(UrlConstant.ARTICLE_CATEGORY_ALL)
			query {
				"start" with "$start"
				"limit" with "$limit"
			}
		}.launch().transform2ApiError(String::toEntity)
	}

	fun getArticleList(start: Int, limit: Int, categoryId: Int): ApiErrorResult<ArticleResult> {
		return httpGet {
			url(UrlConstant.ARTICLE_CATEGORY_LIST.format(categoryId))
			query {
				"start" with "$start"
				"limit" with "$limit"
			}
		}.launch().transform2ApiError(String::toEntity)
	}

	fun getArticleDetail(articleId: Int): ApiErrorResult<ArticleDetailResult> {
		return httpGet {
			url(UrlConstant.ARTICLE_DETAIL.format(articleId))
		}.launch().transform2ApiError(String::toEntity)
	}

	fun likeArticle(likeId: Int): ApiErrorResult<Boolean> {
		return httpGet {
			url(UrlConstant.ARTICLE_LIKE)
			query {
				"like_id" with "$likeId"
				"id_type" with ""
			}
		}.launch().transform2ApiError { JsonParser.parseString(it).asJsonObject.get("success").asBoolean }
	}
}