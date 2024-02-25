package com.ujinturnaway.lowkeytestapp.domain.network

interface PhotosApi {
    suspend fun getPhotos(
        pageSize: Int,
        pageNumber: Int,
        authHeader: String,
    ): ApiResult<PhotoResponseEntity>
}