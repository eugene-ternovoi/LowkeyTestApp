package com.ujinturnaway.lowkeytestapp.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val imageId: String,
    val originalImageUrl: String,
    val portraitImageUrl: String,
    val photographer: String,
)

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val nextPage: Int,
)
