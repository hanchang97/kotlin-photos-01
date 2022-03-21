package com.example.photoalbum

import android.graphics.Color
import kotlin.random.Random

data class AlbumRectangle(
    val color: Int =
        Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
)
