package com.nick.topbook.module.article.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.nick.topbook.data.Resource
import com.nick.topbook.data.errorOrNull
import com.nick.topbook.data.getOrNull
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.model.ArticleCategoryResult
import com.nick.topbook.module.article.model.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {

	private val articleRepository by lazy { ArticleRepository() }

	fun getArticleCategory(start: Int, limit: Int): Flow<Resource<ArticleCategoryResult>> {
		val unknownError = Throwable("unknownError")
		return flow {
			withContext(Dispatchers.IO) {
				val apiErrorResult = articleRepository.getArticleCategory(start, limit)
				apiErrorResult.getOrNull()?.let {
					emit(it)
				}
				apiErrorResult.errorOrNull()?.let {
					emit(it)
				}
			}
		}.flowOn(Dispatchers.IO)
	}

	fun getArticlePagedList(initStart: Int, pageSize: Int, categoryId: Int): Flow<PagingData<Article>> {
		val unknownError = Throwable("unknownError")
		return Pager(PagingConfig(pageSize, 1, false, pageSize), initStart) {
			object : PagingSource<Int, Article>() {
				override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
					return withContext(Dispatchers.IO) {
						val key = params.key ?: initStart
						val articleResult = articleRepository.getArticleList(key, params.loadSize, categoryId)
						articleResult.getOrNull()?.let {
							val pagingDataList = it.data?.articleData?.articles ?: emptyList()
							val nextKey = if (pagingDataList.size < pageSize) null else key + pageSize
							return@withContext LoadResult.Page(pagingDataList, null, nextKey)
						}
						articleResult.errorOrNull()?.let {
							return@withContext LoadResult.Error(it.apiError ?: unknownError)
						}
						return@withContext LoadResult.Error(unknownError)
					}
				}
			}
		}.flow
	}

	fun likeArticle(articleId: Int) {
	}
}