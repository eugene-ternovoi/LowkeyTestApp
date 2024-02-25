package com.ujinturnaway.lowkeytestapp.domain.usecase

import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.repository.PhotoRepository

class GetPhoto(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(photoId: Int): PhotoEntity? {
        return photoRepository.getPhoto(photoId)
    }
}