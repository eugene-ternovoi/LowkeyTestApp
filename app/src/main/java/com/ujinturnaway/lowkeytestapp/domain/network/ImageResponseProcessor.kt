package com.ujinturnaway.lowkeytestapp.domain.network

import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity

class ImageResponseProcessor : ResponseProcessor<PhotosApiResponse, PhotoResponseEntity> {
    override fun process(response: PhotosApiResponse): PhotoResponseEntity {
        return PhotoResponseEntity(
            response.pageNumber, response.photos.map {
                PhotoEntity(
                    it.imageId,
                    it.source.originalImageUrl,
                    it.source.portraitImageUrl,
                    it.photographer
                )
            }
        )
    }
}