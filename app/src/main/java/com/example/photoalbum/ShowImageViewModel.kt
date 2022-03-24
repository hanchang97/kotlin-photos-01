package com.example.photoalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.data.ImageData
import com.example.photoalbum.model.ShowImageRepositoryRemoteImpl
import kotlinx.coroutines.launch
import org.json.JSONArray

class ShowImageViewModel: ViewModel() {

    private val _imageDataList = MutableLiveData<List<ImageData>>()
    val imageDataList : LiveData<List<ImageData>> = _imageDataList

    private val showImageRepository = ShowImageRepositoryRemoteImpl()

    fun getImage(){
        viewModelScope.launch {
            val resultString = showImageRepository.downloadJson()
            _imageDataList.postValue(jsonObjectList(resultString))
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
                )
            )
        }
        return jsonList
    }
}