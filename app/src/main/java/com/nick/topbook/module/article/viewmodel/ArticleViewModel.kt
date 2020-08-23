package com.nick.topbook.module.article.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nick.topbook.data.errorOrNull
import com.nick.topbook.data.getOrNull
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.model.ArticleRepository
import com.nick.topbook.module.article.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {

	private val articleRepository by lazy { ArticleRepository() }
	private val unknownError = Throwable("unknownError")

	fun getArticleCategory(start: Int, limit: Int): Flow<List<Category>> {
		return flow<List<Category>> {
			withContext(Dispatchers.IO) {
				val apiErrorResult = articleRepository.getArticleCategory(start, limit)
				apiErrorResult.getOrNull()?.let {
					val categoryList = it.data?.categoryData?.categories ?: emptyList()
					emit(categoryList)
				}
				apiErrorResult.errorOrNull()?.let {
					throw it.apiError ?: unknownError
				}
			}
		}.flowOn(Dispatchers.IO)
	}

	fun getArticlePagedList(initStart: Int, pageSize: Int, categoryId: Int): Flow<PagingData<Article>> {
		return Pager(PagingConfig(pageSize, 1, false, pageSize), initStart) {
			object : PagingSource<Int, Article>() {
				override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
					delay(1000)
					return withContext(Dispatchers.IO) {
						val key = params.key
						val articleResult = articleRepository.getArticleList(key ?: initStart, params.loadSize, categoryId)
						articleResult.getOrNull()?.let {
							val pagingDataList = it.data?.articleData?.articles ?: emptyList()
							val nextKey = if (pagingDataList.size < pageSize) null else key?.plus(pageSize)
							return@withContext LoadResult.Page(pagingDataList, null, nextKey)
						}
						articleResult.errorOrNull()?.let {
							return@withContext LoadResult.Error(it.apiError ?: unknownError)
						}
						return@withContext LoadResult.Error(unknownError)
					}
				}
			}
		}.flow.cachedIn(viewModelScope)
	}

	fun likeArticle(articleId: Int) {
	}
}