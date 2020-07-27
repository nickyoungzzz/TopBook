package com.nick.topbook.constant

class UrlConstant {

	companion object {

		private const val HOST = "topbook.cc"

		private const val BASE_URL = "https://$HOST/webapi"

		const val LIFE_TOPIC_PAGE = "$BASE_URL/community/topic/page"

		const val LIFE_TOPIC_DETAIL = "$BASE_URL/community/topic/query"

		const val ARTICLE_CATEGORY_ALL = "$BASE_URL/content/category/page"

		const val ARTICLE_CATEGORY_LIST = "$BASE_URL/content/article/%s/page"

		const val ARTICLE_DETAIL = "$BASE_URL/content/article/query/%s"

		const val ARTICLE_LIKE = "$BASE_URL/user/like"

		const val ARTICLE_REVOKE_LIKE = "$BASE_URL/user/like"
	}
}