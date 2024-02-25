package com.ujinturnaway.lowkeytestapp.presentation.ui.screen

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ujinturnaway.lowkeytestapp.R
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val photosPagingItems = viewModel.pokemonPagingDataFlow.collectAsLazyPagingItems()
    val swipeRefreshState =
        rememberSwipeRefreshState(photosPagingItems.loadState.refresh is LoadState.Loading)
    if (photosPagingItems.loadState.refresh is LoadState.Error) {
        val snackbarHostState = remember { SnackbarHostState() }
        if (photosPagingItems.loadState.refresh is LoadState.Error) {
            LaunchedEffect(key1 = snackbarHostState) {
                snackbarHostState.showSnackbar(
                    (photosPagingItems.loadState.refresh as LoadState.Error).error.message
                        ?: "Error occurred :("
                )
            }
        }
    }
    val listState = photosPagingItems.rememberLazyListState()

    SwipeRefresh(state = swipeRefreshState, onRefresh = { photosPagingItems.refresh() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Vertical)
                .asPaddingValues(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
        ) {
            items(
                count = photosPagingItems.itemCount,
                key = photosPagingItems.itemKey { it.imageId },
            ) { index ->
                val photoEntity = photosPagingItems[index]
                photoEntity?.let { PhotoItem(it, navController) }
            }

            item {
                if (photosPagingItems.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun PhotoItem(photo: PhotoEntity, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxSize(),
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
            model = ImageRequest.Builder(LocalContext.current.applicationContext)
                .data(photo.portraitImageUrl)
                .diskCacheKey(photo.imageId)
                .memoryCacheKey(photo.imageId)
                .build(),
            onSuccess = { showShimmer.value = false },
            contentScale = ContentScale.FillWidth,
            contentDescription = null,

            onError = {
                Log.d(
                    "ImageLoader",
                    "Bad image loading result result: ${it.result}"
                )
                showShimmer.value = false
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 16.dp),
                text = photo.photographer,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.weight(1f))
            IconButton(modifier = Modifier
                .align(Alignment.CenterVertically), onClick = {
                navController.navigate("photo/${photo.imageId}")
            }) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.more_info_icon),
                    contentDescription = ""
                )
            }


        }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 10000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}

