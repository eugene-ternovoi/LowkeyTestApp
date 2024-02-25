package com.ujinturnaway.lowkeytestapp.domain.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.database.PhotosDatabase
import com.ujinturnaway.lowkeytestapp.domain.database.RemoteKeyEntity

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator(
    private val photosDatabase: PhotosDatabase,
    private val photosApi: PhotosApi,
    private val authProvider: AuthProvider,
) : RemoteMediator<Int, PhotoEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {
        Log.d("PhotosRemoteMediator", "loadType: ${loadType}")
        Log.d("PhotosRemoteMediator", "state: ${state}")
        val pageNumber = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = photosDatabase.remoteKeyDao.getById(REMOTE_KEY_ID)
                if (remoteKey == null || remoteKey.nextPage == 0)
                    return MediatorResult.Success(endOfPaginationReached = true)
                remoteKey.nextPage
            }
        }

        val apiResponse: ApiResult<PhotoResponseEntity> = photosApi.getPhotos(
            pageNumber = pageNumber,
            pageSize = state.config.pageSize,
            authHeader = authProvider.provideAuthHeader(),
        )

        return when (apiResponse) {
            is ApiResult.Success -> {
                handleResult(apiResponse.data, loadType, state)
            }

            is ApiResult.Error -> {
                val message =
                    "Network error occurred Code: ${apiResponse.code}, Message: ${apiResponse.message}"
                Log.d(TAG, message)
                MediatorResult.Error(Throwable(message))
            }

            is ApiResult.Exception -> {
                Log.d(TAG, "Exception", apiResponse.exception)
                MediatorResult.Error(apiResponse.exception)
            }
        }
    }


    private suspend fun handleResult(
        data: PhotoResponseEntity,
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {
        val nextPage = data.page + 1
        photosDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                photosDatabase.photosDao.clearAll()
                photosDatabase.remoteKeyDao.deleteById(REMOTE_KEY_ID)
            }
            photosDatabase.photosDao.insertAll(data.photos)
            photosDatabase.remoteKeyDao.insert(
                RemoteKeyEntity(
                    id = REMOTE_KEY_ID,
                    nextPage = nextPage,
                )
            )
        }
        return MediatorResult.Success(endOfPaginationReached = data.photos.size < state.config.pageSize)
    }

    companion object {
        private const val REMOTE_KEY_ID = "ANY_STRING :)"
        private const val TAG = "PhotosRemoteMediator"
    }
}



