package com.nick.topbook.data

sealed class Resource

data class RespSuccess<T>(val data: T?) : Resource()

data class RespError(val apiError: ApiError?) : Resource()

object RespLoading : Resource()

@Suppress("UNCHECKED_CAST") class ResourceObserver<T>(private val resource: Resource) {

	fun succeed(t: (r: T) -> Unit) {
		if (resource is RespSuccess<*>) t(resource.data as T)
	}

	fun lost(t: (r: ApiError?) -> Unit) {
		if (resource is RespError) t(resource.apiError)
	}

	fun loading(t: () -> Unit) {
		if (resource is RespLoading) t()
	}
}