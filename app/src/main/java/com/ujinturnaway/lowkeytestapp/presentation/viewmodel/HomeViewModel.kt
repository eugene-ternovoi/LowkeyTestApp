package com.ujinturnaway.lowkeytestapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    photoRepository: PhotoRepository,
) : ViewModel() {
    val pokemonPagingDataFlow: Flow<PagingData<PhotoEntity>> = photoRepository.getPhotosListFlow()
}