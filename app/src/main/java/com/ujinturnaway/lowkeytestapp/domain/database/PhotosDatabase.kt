package com.ujinturnaway.lowkeytestapp.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PhotoEntity::class, RemoteKeyEntity::class],
    version = 1,
)
abstract class PhotosDatabase : RoomDatabase() {
    abstract val photosDao: PhotosDao
    abstract val remoteKeyDao: RemoteKeyDao
}