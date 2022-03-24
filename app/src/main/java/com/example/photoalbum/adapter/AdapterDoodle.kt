package com.example.photoalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoalbum.R
import com.example.photoalbum.data.ImageData
import com.example.photoalbum.databinding.ItemDoodleImageBinding
import com.example.photoalbum.util.DoodleDiffCallBack

class AdapterDoodle : ListAdapter<ImageData, AdapterDoodle.MyViewHolder>(DoodleDiffCallBack) {

    class MyViewHolder(private val binding: ItemDoodleImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageDataObject: ImageData) {
            Glide.with(binding.root).load(imageDataObject.image).override(110, 50)
                .into(binding.ivDoodleImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDoodleImageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_doodle_image, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}