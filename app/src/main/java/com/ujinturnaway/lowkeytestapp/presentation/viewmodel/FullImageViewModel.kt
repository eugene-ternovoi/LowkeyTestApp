package com.ujinturnaway.lowkeytestapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.usecase.GetPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhoto: GetPhoto,
) : ViewModel() {
    val state = MutableStateFlow<State>(State.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadPhoto(savedStateHandle.get<Int>("id")!!)
            } catch (exception: Exception) {
                state.value = State.Error
            }
        }
    }

    private suspend fun loadPhoto(id: Int) {
        val photo = getPhoto(id)

        if (photo == null) {
            state.value = State.Error
            return
        }

        state.value = State.Success(photo)
    }
}

sealed class State {
    object Loading : State()
    data class Success(val photoEntity: PhotoEntity) : State()
    object Error : State()
}