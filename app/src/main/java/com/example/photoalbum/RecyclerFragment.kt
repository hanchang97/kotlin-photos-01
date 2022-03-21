package com.example.photoalbum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoalbum.adapter.AdapterGridAlbum
import com.example.photoalbum.data.AlbumRectangle
import com.example.photoalbum.databinding.FragmentRecyclerBinding
import com.example.photoalbum.decoration.RecyclerDecoration

class RecyclerFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerBinding
    private lateinit var adapterGridAlbum: AdapterGridAlbum

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridList = createAlbumRectangle()
        Log.d("AppTest", gridList.toString())

        setRecyclerView()
    }

    fun setRecyclerView() {
        adapterGridAlbum = AdapterGridAlbum()
        binding.rvGridAlbum.adapter = adapterGridAlbum
        binding.rvGridAlbum.layoutManager = GridLayoutManager(context, 4)
        adapterGridAlbum.submitList(createAlbumRectangle())
        binding.rvGridAlbum.addItemDecoration(RecyclerDecoration(30, 50, 0, 20))
    }

    fun createAlbumRectangle(): List<AlbumRectangle> {
        val albumRectangleList = mutableListOf<AlbumRectangle>()
        for (i in 0 until 40) {
            albumRectangleList.add(AlbumRectangle(i))
        }
        return albumRectangleList
    }
}