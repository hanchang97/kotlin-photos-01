package com.example.photoalbum.util

import androidx.recyclerview.widget.DiffUtil
import com.example.photoalbum.data.AlbumRectangle
import com.example.photoalbum.data.Json
import com.example.photoalbum.data.ShowImage

object DoodleDiffCallBack: DiffUtil.ItemCallback<Json>() {
    override fun areItemsTheSame(
        oldItem: Json,
        newItem: Json
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: Json,
        newItem: Json
    ): Boolean {
        return oldItem == newItem
    }

}