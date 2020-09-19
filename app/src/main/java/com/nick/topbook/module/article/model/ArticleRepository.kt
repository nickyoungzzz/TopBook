package com.nick.topbook.module.article.model

import com.google.gson.JsonParser
import com.nick.easygo.core.httpGet
import com.nick.easygo.result.httpResult2Any
import com.nick.topbook.constant.UrlConstant
import com.nick.topbook.data.ApiResult
import com.nick.topbook.data.toApiResult

class ArticleRepository {

	fun getArticleCategory(start: Int, limit: Int): ApiResult<ArticleCategoryResult?> =
		httpGet {
			url(UrlConstant.ARTICLE_CATEGORY_ALL)
			query {
				"start" with "$start"; "limit" with "$limit"
			}
		}.send().toApiResult { it.httpResult2Any() }

	fun getArticleList(start: Int, limit: Int, categoryId: Int): ApiResult<ArticleResult?> {
		return httpGet {
			url(UrlConstant.ARTICLE_CATEGORY_LIST.format(categoryId))
			query {
				"start" with "$start"
				"limit" with "$limit"
			}
		}.send().toApiResult { it.httpResult2Any() }
	}

	fun getArticleDetail(articleId: Int): ApiResult<ArticleDetailResult?> {
		return httpGet {
			url(UrlConstant.ARTICLE_DETAIL.format(articleId))
		}.send().toApiResult { it.httpResult2Any() }
	}

	fun likeArticle(likeId: Int): ApiResult<Boolean> =
		httpGet {
			url(UrlConstant.ARTICLE_LIKE)
			query {
				"like_id" with "$likeId"
				"id_type" with ""
			}
		}.send().toApiResult { JsonParser.parseString(it).asJsonObject.get("success").asBoolean }
}