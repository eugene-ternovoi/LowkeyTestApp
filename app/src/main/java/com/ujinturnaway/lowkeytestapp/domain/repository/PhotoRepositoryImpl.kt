package com.ujinturnaway.lowkeytestapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.database.PhotosDatabase
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl(
    private val pager: Pager<Int, PhotoEntity>,
    private val photosDatabase: PhotosDatabase,
): PhotoRepository {
    override fun getPhotosListFlow(): Flow<PagingData<PhotoEntity>> {
        return pager.flow
    }

    override suspend fun getPhoto(photoId: Int): PhotoEntity? {
        return photosDatabase.photosDao.getPhoto(photoId)
    }

}