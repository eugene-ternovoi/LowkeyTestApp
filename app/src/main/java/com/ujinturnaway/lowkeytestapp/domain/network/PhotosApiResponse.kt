package com.ujinturnaway.lowkeytestapp.domain.network

import com.google.gson.annotations.SerializedName

data class PhotosApiResponse(
    @SerializedName("page") val pageNumber: Int,
    @SerializedName("photos") val photos: List<PhotosInResponse>,
)

data class PhotosInResponse(
    @SerializedName("id") val imageId: String,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("src") val source: ImageSourceResponse,
)

data class ImageSourceResponse(
    @SerializedName("original") val originalImageUrl: String,
    @SerializedName("portrait") val portraitImageUrl: String,
)
