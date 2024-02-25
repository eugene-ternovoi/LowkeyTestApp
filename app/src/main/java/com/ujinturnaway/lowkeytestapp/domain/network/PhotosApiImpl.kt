package com.ujinturnaway.lowkeytestapp.domain.network

class PhotosApiImpl(
    private val imagesService: ImagesService,
    private val responseProcessor: ImageResponseProcessor,
) : BaseNetworkRequest(), PhotosApi {

    override suspend fun getPhotos(
        pageSize: Int,
        pageNumber: Int,
        authHeader: String,
    ): ApiResult<PhotoResponseEntity> {
        return executeRequest(responseProcessor) {
            imagesService.curated(
                authHeader = authHeader,
                pageSize = pageSize,
                pageNumber = pageNumber,
            )
        }
    }
}