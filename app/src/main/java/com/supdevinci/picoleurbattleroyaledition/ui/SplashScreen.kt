package com.supdevinci.picoleurbattleroyaledition.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import com.supdevinci.picoleurbattleroyaledition.R
import com.supdevinci.picoleurbattleroyaledition.ui.navigation.Routes
import com.supdevinci.picoleurbattleroyaledition.viewmodel.ApiViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: ApiViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val progress = remember { Animatable(0f) }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(GifDecoder.Factory()) }
            .build()
    }

    LaunchedEffect(Unit) {
        viewModel.loadTrending()
    }

    LaunchedEffect(isLoading) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        )
        if (!isLoading) {
            navController.navigate(Routes.LEADERBOARD) {
                popUpTo(Routes.SPLASH) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AsyncImage(
            model = R.raw.splashscreen_gif,
            contentDescription = "Loading Animation",
            imageLoader = imageLoader,
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFFBB86FC),
            trackColor = Color.LightGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Chargement : ${(progress.value * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
