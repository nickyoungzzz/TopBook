package com.nick.topbook.module.article.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nick.topbook.data.*
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.model.ArticleRepository
import com.nick.topbook.module.article.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {

	private val articleRepository by lazy { ArticleRepository() }
	private val unknownError = Throwable("unknownError")

	fun getArticleCategory(start: Int, limit: Int): Flow<Resource<List<Category>>> {
		return flow {
			println(Thread.currentThread().name)
			emit(Resource.RespLoading)
			val apiResult = articleRepository.getArticleCategory(start, limit)
			apiResult.getOrNull()?.let {
				val categoryList = it.data?.categoryData?.categories
				emit(Resource.RespSuccess(categoryList))
			}
			apiResult.errorOrNull()?.let {
				emit(Resource.RespError(it.apiError))
			}
		}.flowOn(Dispatchers.IO)
	}

	fun getArticlePagedList(
		initStart: Int,
		pageSize: Int,
		categoryId: Int,
	): Flow<PagingData<Article>> {
		return Pager(PagingConfig(pageSize, 1, false, pageSize), initStart) {
			object : PagingSource<Int, Article>() {
				override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
					delay(1000)
					return withContext(Dispatchers.IO) {
						val key = params.key
						val apiResult = articleRepository.getArticleList(
							key ?: initStart,
							params.loadSize,
							categoryId
						)
						apiResult.res?.let {
							val pagingDataList = it.articleData.articles
							val nextKey =
								if (pagingDataList.size < pageSize) null else key?.plus(pageSize)
							return@withContext LoadResult.Page(pagingDataList, null, nextKey)
						}
						apiResult.err?.let {
							return@withContext LoadResult.Error(it)
						}
						return@withContext LoadResult.Error(unknownError)
					}
				}
			}
		}.flow.cachedIn(viewModelScope).flowOn(Dispatchers.Main)
	}

	fun likeArticle(articleId: Int) {
	}
}