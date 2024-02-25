package com.ujinturnaway.lowkeytestapp.domain.repository

import androidx.paging.PagingData
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhotosListFlow() : Flow<PagingData<PhotoEntity>>
    suspend fun getPhoto(photoId: Int) : PhotoEntity?
}