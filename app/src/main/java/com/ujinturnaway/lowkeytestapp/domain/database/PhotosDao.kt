package com.ujinturnaway.lowkeytestapp.domain.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PhotoEntity>)

    @Query("SELECT * FROM photos")
    fun pagingSource(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM photos")
    suspend fun clearAll()

    @Query("SELECT * FROM photos WHERE imageId=:id")
    suspend fun getPhoto(id: Int): PhotoEntity?
}

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    suspend fun getById(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE id = :id")
    suspend fun deleteById(id: String)
}
