package com.ujinturnaway.lowkeytestapp.domain.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImagesService {
    @GET("v1/curated")
    suspend fun curated(
        @Header("Authorization") authHeader: String,
        @Query("per_page") pageSize: Int,
        @Query("page") pageNumber: Int,
    ): Response<PhotosApiResponse>
}