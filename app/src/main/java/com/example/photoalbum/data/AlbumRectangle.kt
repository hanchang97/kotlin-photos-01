package com.example.photoalbum.data

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.random.Random

data class AlbumRectangle(
    val id : Int,
    var bitmap: Bitmap? = null,
    val color: Int =
        Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
)
