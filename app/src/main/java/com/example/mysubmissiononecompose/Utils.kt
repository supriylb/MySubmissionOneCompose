package com.example.mysubmissiononecompose

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    }
}

@Composable
fun MyAsyncImage(
    image: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current
    val imageRequest = remember(image) {
        ImageRequest.Builder(context)
            .data(image)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            .crossfade(enable = true)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .build()
    }

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}
