package com.nick.topbook.data

fun <T : Any> ApiErrorResult<T>.getOrNull(): Resource.RespSuccess<T>? = if (this.isSuccess) Resource.RespSuccess(res) else null

fun <T : Any> ApiErrorResult<T>.errorOrNull(): Resource.RespError<T>? = if (!this.isSuccess) Resource.RespError(err) else null