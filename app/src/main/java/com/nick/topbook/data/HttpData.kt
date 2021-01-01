package com.nick.topbook.data

import com.nick.easygo.result.HttpError
import com.nick.easygo.result.HttpResult
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias ApiResult<T> = AppResult<T, AppError>

data class AppError(val errCode: Int, val errMsg: String) : Throwable(errMsg)

val <T> ApiResult<T>.res: T?
	get() = this.t

val <T> ApiResult<T>.err: AppError?
	get() = this.f

private val unknownError = AppError(0, "unknown error")

fun Throwable?.toAppError(): AppError? = when (this) {
	is HttpError -> AppError(0, "")
	is SocketTimeoutException -> AppError(111, "请求已超时！")
	is ConnectException, is UnknownHostException -> AppError(111, "网络连接失败！")
	else -> null
}

data class AppResult<T, F>(val code: Int, val headers: Map<String, List<String>>, val url: String, val t: T?, val f: F?)

fun <T> HttpResult<T>.toApiResult(): ApiResult<T> {
	return ApiResult(code, headers, url, res, error.toAppError())
}
