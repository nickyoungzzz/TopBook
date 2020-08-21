package com.nick.topbook.data

sealed class Resource<T : Any> {
	data class RespSuccess<T : Any>(val data: T?) : Resource<T>()
	data class RespError<T : Any>(val apiError: ApiError?) : Resource<T>()
	object RespLoading : Resource<Any>()
}