package com.ujinturnaway.lowkeytestapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ujinturnaway.lowkeytestapp.presentation.viewmodel.FullImageViewModel
import com.ujinturnaway.lowkeytestapp.presentation.viewmodel.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullImageScreen(
    navController: NavController,
) {
    val viewModel: FullImageViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state) {
            State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is State.Success -> {
                val photo = state.photoEntity
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = photo.photographer,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                )

                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current.applicationContext)
                        .data(photo.originalImageUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight

                )
            }

            State.Error -> {
                Text("Error :(")
            }
        }


    }
}