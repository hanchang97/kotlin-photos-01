package com.example.photoalbum

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoalbum.adapter.AdapterGridAlbum
import com.example.photoalbum.adapter.AdapterShowImage
import com.example.photoalbum.data.ShowImage
import com.example.photoalbum.databinding.ActivityShowImageBinding
import com.example.photoalbum.decoration.RecyclerDecoration
import kotlin.collections.ArrayList as ArrayList

class ShowImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowImageBinding
    private lateinit var adapterShowImage: AdapterShowImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_image)

        setRecyclerView()
        setImageView()
        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.navi_goto_doodles -> {
                    Log.d("AppTest", "+ icon clicked")
                    // doodles 액티비티로 이동되게 구현하기

                    true
                }
                else -> false
            }
        }
    }

    private fun setRecyclerView(){
        adapterShowImage = AdapterShowImage()
        binding.rvShowImage.adapter = adapterShowImage
        binding.rvShowImage.layoutManager = GridLayoutManager(this, 3)
        binding.rvShowImage.addItemDecoration(RecyclerDecoration(0, 0, 0, 0))
    }

    private fun setImageView() {
        val imageList = intent.getSerializableExtra("imageList") as ArrayList<*>
        val showImageList = mutableListOf<ShowImage>()
        for (i in 0 until imageList.size) {
            val bitmap = uriToBitmap(imageList[i] as String)
            bitmap.scale(100, 100)
            val showImage = ShowImage(i, bitmap)
            showImageList.add(showImage)
            Log.d("AppTest", "${imageList[i]}")
        }
        adapterShowImage.submitList(showImageList)
    }

    private fun uriToBitmap(image: String) : Bitmap {
        return BitmapFactory.decodeFile(image)
    }
}