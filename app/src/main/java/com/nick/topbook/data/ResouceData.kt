package com.nick.topbook.data

sealed class Resource<out T> {
	data class RespSuccess<T>(val data: T?) : Resource<T>()
	data class RespError(val apiError: ApiError?) : Resource<Nothing>()
	object RespLoading : Resource<Nothing>()
}