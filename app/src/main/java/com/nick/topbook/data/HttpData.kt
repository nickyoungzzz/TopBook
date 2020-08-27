package com.nick.topbook.data

import com.nick.easygo.result.HttpError
import com.nick.easygo.result.HttpRawResult
import com.nick.easygo.result.HttpResult
import com.nick.easygo.result.httpResult2Any
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias ApiResult<T> = HttpResult<T, ApiError>

data class ApiError(val errCode: Int, val errMsg: String) : Throwable(errMsg)

inline fun <T> HttpRawResult.toApiResult(crossinline t: (String?) -> T) =
    mapResult<T, ApiError> {
        result {
            t.invoke(it)
        }
        error {
            it.toApiError()
        }
    }

val <T> ApiResult<T>.res: T?
    get() = this.result

val <T> ApiResult<T>.err: ApiError?
    get() = this.error

private val unknownError = ApiError(0, "unknown error")

fun Throwable?.toApiError(): ApiError? = when (this) {
    is HttpError -> this.error.httpResult2Any<ApiError>()
    is SocketTimeoutException -> ApiError(111, "请求已超时！")
    is ConnectException, is UnknownHostException -> ApiError(111, "网络连接失败！")
    else -> null
}