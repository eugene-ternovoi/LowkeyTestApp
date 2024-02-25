package com.ujinturnaway.lowkeytestapp.domain.network

import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity

data class PhotoResponseEntity(
    val page: Int,
    val photos: List<PhotoEntity>,
)
