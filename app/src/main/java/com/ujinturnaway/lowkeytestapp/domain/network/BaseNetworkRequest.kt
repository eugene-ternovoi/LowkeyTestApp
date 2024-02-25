package com.ujinturnaway.lowkeytestapp.domain.network

import android.util.Log
import retrofit2.Response


abstract class BaseNetworkRequest {
    suspend fun <T : Any, R : Any> executeRequest(
        responseProcessor: ResponseProcessor<T, R>,
        execute: suspend () -> Response<T>,
    ): ApiResult<R> {
        return try {
            val response = execute()
            val body = response.body()

            if (body == null) {
                return ApiResult.Error(response.code(), SUCCESS_EMPTY_BODY)
            }

            if (response.isSuccessful) {
                return ApiResult.Success(responseProcessor.process(body))
            }

            val errorBody = response.errorBody()

            if (errorBody == null) {
                return ApiResult.Error(response.code(), ERROR_EMPTY_BODY)
            }

            ApiResult.Error(response.code(), errorBody.string())

        } catch (e: Throwable) {
            Log.d("NETWORK REQUEST", "executeRequest: ${e}")
            ApiResult.Exception(e)
        }
    }

    companion object {
        private const val SUCCESS_EMPTY_BODY = "Response success, but body is empty"
        private const val ERROR_EMPTY_BODY = "Error, error body is empty"
    }
}