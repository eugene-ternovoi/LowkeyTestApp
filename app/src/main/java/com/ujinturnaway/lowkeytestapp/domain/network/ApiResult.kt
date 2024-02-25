package com.ujinturnaway.lowkeytestapp.domain.network

sealed class ApiResult<out T : Any> {
    data class Success<T : Any>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    data class Exception(val exception: Throwable) : ApiResult<Nothing>()
}
