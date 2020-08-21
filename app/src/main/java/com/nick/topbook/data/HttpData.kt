package com.nick.topbook.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nick.easyhttp.result.HttpResult
import com.nick.easyhttp.result.Result
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias ApiErrorResult<T> = Result<T, ApiError, ApiError>

data class ApiError(val errCode: Int, val errMsg: String) : Throwable(errMsg)

inline fun <T> HttpResult.transform2ApiError(crossinline t: (String) -> T) = transform<T, ApiError, ApiError> {
	success { t(it) }
	error {
		it.toApiError()
	}
	exception { it.toApiError() }
}

val <T> ApiErrorResult<T>.res: T?
	get() = if (this.isSuccess) this.success else null

val <T> ApiErrorResult<T>.err: ApiError?
	get() = when {
		this.isSuccess -> null
		this.isError -> this.error
		this.isException -> this.exception
		else -> null
	}


inline fun <reified T> String.toEntity(): T = Gson().fromJson(this, T::class.java)

fun <T> String.toEntityList(): List<T> = Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)

fun String.toApiError(): ApiError = this.toEntity()

fun Throwable?.toApiError(): ApiError = when (this) {
	is SocketTimeoutException -> ApiError(111, "请求已超时！")
	is ConnectException, is UnknownHostException -> ApiError(111, "网络连接失败！")
	else -> ApiError(222, "")
}