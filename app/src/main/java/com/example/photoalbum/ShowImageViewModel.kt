package com.example.photoalbum

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.photoalbum.data.ImageData
import com.example.photoalbum.model.ShowImageRepositoryRemoteImpl
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.*

class ShowImageViewModel: ViewModel() {

    private var imageList = listOf<ImageData>()
    private val _imageDataList = MutableLiveData<List<ImageData>>()
    var imageDataList : LiveData<List<ImageData>> = _imageDataList

    init {
        _imageDataList.value = imageList
    }

    private val showImageRepository = ShowImageRepositoryRemoteImpl()

    fun getImage(){
        viewModelScope.launch {
            val resultString = showImageRepository.downloadJson()
            imageList = jsonObjectList(resultString)
            _imageDataList.postValue(imageList)
        }
    }

    private fun jsonObjectList(jsonString: String): List<ImageData> {
        val jsonArray = JSONArray(jsonString)
        val jsonList = mutableListOf<ImageData>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonList.add(
                ImageData(
                    jsonObject.getString("title"),
                    jsonObject.getString("image"),
                    jsonObject.getString("date"),
                    selected = false,
                    checkBoxVisible = false
                )
            )
        }
        return jsonList
    }

    fun updateCheck(selectedInx: Int){
        imageList.forEach {
            it.checkBoxVisible = true
        }
        imageList[selectedInx].selected = true

        _imageDataList.value = imageList
    }

    fun changeCheckedState(checkedInx: Int){
        imageList[checkedInx].selected = !imageList[checkedInx].selected
        _imageDataList.value = imageList
    }

    fun selectImageLoad(context: Context): List<File> {
        val fileList = mutableListOf<File>()
        val manager = Glide.with(context)
        imageList.forEach {
            if(it.selected) {
                val file = manager.downloadOnly().load(it.image).submit().get()
                fileList.add(file)
            }
        }
        return fileList
    }

    fun saveImage(file: File) {
        var localFile = File(Environment.getExternalStoragePublicDirectory(file.path).path)
        if (!localFile.exists()) {
            localFile.mkdirs()
        }
        val filePath = Environment.getExternalStoragePublicDirectory(file.path).path + System.currentTimeMillis() + ".jpeg"
        localFile = File(filePath)

        try {
            val fileInputStream = FileInputStream(file)
            val bufferedInputStream = BufferedInputStream(fileInputStream)
            val byteArrayOutputStream = ByteArryayOutputStream()

            val imageByte = ByteArray(1024)
            var current = 0
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}