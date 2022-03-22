package com.example.photoalbum

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoalbum.data.AlbumRectangle

class AlbumViewModel : ViewModel() {

    var gridList = MutableLiveData<List<AlbumRectangle>>()

}