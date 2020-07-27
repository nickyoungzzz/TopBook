package com.nick.topbook.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> ApiErrorResult<T>.getOrNull(): RespSuccess<T>? = if (this.isSuccess) RespSuccess(res) else null

fun <T> ApiErrorResult<T>.errorOrNull(): RespError? = if (!this.isSuccess) RespError(err) else null

inline fun <T> LiveData<Resource>.observeResource(lifecycleOwner: LifecycleOwner, crossinline observer: ResourceObserver<T>.() -> Unit) = this.observe(lifecycleOwner, {
	ResourceObserver<T>(it).apply(observer)
})

fun MutableLiveData<Resource>.postResource(vararg resources: Resource?) = resources.filterNotNull().forEach { this.postValue(it) }

fun MutableLiveData<Resource>.setResource(vararg resources: Resource?) = resources.filterNotNull().forEach { this.value = it }