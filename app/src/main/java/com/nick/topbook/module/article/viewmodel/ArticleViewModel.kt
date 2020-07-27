package com.nick.topbook.module.article.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.nick.topbook.data.*
import com.nick.topbook.module.article.model.Article
import com.nick.topbook.module.article.model.ArticleRepository
import com.nick.topbook.module.article.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {

	private val articleRepository = ArticleRepository()

	private val articleListLiveData: MutableLiveData<Resource> by lazy {
		MutableLiveData<Resource>()
	}

	private val articleCategoryLiveData: MutableLiveData<Resource> by lazy {
		MutableLiveData<Resource>()
	}

	private val articleDetailLiveData: MutableLiveData<Resource> by lazy {
		MutableLiveData<Resource>()
	}

	@SuppressLint("RestrictedApi") fun getArticleCategory(start: Int, limit: Int): LiveData<Resource> {
		var error: ApiError? = null
		val liveData = object : ComputableLiveData<List<Category>>() {
			override fun compute(): List<Category> {
				val apiErrorResult = articleRepository.getArticleCategory(start, limit)
				error = apiErrorResult.err
				return apiErrorResult.res?.categoryData?.categories ?: emptyList()
			}
		}.liveData
		return Transformations.switchMap(liveData) {
			val respSuccess: RespSuccess<List<Category>>? = it?.let { it1 -> if (it1.isEmpty() && error != null) null else RespSuccess(it1) }
			val respError: RespError? = error?.let { it1 -> RespError((it1)) }
			return@switchMap articleCategoryLiveData.apply { setResource(respSuccess, respError) }
		}
	}

	fun getArticlePagedList(initStart: Int, pageSize: Int, categoryId: Int): LiveData<Resource> {
		var err: ApiError? = null
		val liveData = LivePagedListBuilder(object : DataSource.Factory<Int, Article>() {
			override fun create(): DataSource<Int, Article> {
				return object : PositionalDataSource<Article>() {

					override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Article>) {
						val articleResult = articleRepository.getArticleList(params.requestedStartPosition, params.pageSize, categoryId)
						err = articleResult.err
						val pagedList = articleResult.res?.articleData?.articles ?: emptyList()
						callback.onResult(pagedList, params.requestedStartPosition)
					}

					override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Article>) {
						val articleResult = articleRepository.getArticleList(params.startPosition, params.loadSize, categoryId)
						err = articleResult.err
						val pagedList = articleResult.res?.articleData?.articles ?: emptyList()
						callback.onResult(pagedList)
					}
				}
			}
		}, PagedList.Config.Builder().setPageSize(pageSize).setInitialLoadSizeHint(pageSize).setEnablePlaceholders(false)
			.setPrefetchDistance(1).build()).setInitialLoadKey(initStart).build()
		return Transformations.switchMap(liveData) {
			val respSuccess: RespSuccess<PagedList<Article>>? = it.let { if (it.isEmpty() && err != null) null else RespSuccess(it) }
			val respError: RespError? = err?.let { it1 -> RespError(it1) }
			return@switchMap articleListLiveData.apply { setResource(respSuccess, respError) }
		}
	}

	fun getArticleDetail(articleId: Int) {
		viewModelScope.launch(Dispatchers.IO) {
			val articleDetailResult = articleRepository.getArticleDetail(articleId)
			withContext(Dispatchers.Main) {
				articleDetailLiveData.setResource(articleDetailResult.getOrNull(), articleDetailResult.errorOrNull())
			}
		}
	}

	fun getArticleDetail() = articleDetailLiveData

	fun likeArticle(articleId: Int) {
	}
}