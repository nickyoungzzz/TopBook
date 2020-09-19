package com.nick.topbook.data

fun <T : Any> ApiResult<T?>.getOrNull(): Resource.RespSuccess<T>? =
	res?.let { Resource.RespSuccess(it) }

fun <T : Any> ApiResult<T?>.errorOrNull(): Resource.RespError? =
	err?.let { Resource.RespError(it) }