package com.example.photoalbum

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoalbum.adapter.AdapterDoodle
import com.example.photoalbum.adapter.AdapterShowImage
import com.example.photoalbum.data.ImageData
import com.example.photoalbum.data.ShowImage
import com.example.photoalbum.databinding.ActivityShowImageBinding
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ShowImageActivity : AppCompatActivity() {

    private val viewModel : ShowImageViewModel by viewModels()

    private lateinit var binding: ActivityShowImageBinding
    private lateinit var adapterShowImage: AdapterShowImage
    private lateinit var adapterDoodle: AdapterDoodle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_image)

        setRecyclerView()
        CoroutineScope(Job() + Dispatchers.Main).launch {
            setImageView()
        }

        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navi_goto_doodles -> {
                    Log.d("AppTest", "+ icon clicked")
                    // doodles 액티비티로 이동되게 구현하기
                    turnOffPhotoView()
                    turnOnDoodleView()
                    true
                }

                else -> false
            }
        }


        viewModel.imageDataList.observe(this){
            Log.d("AppTest", "list observe")
            adapterDoodle.submitList(it.toMutableList())
            adapterDoodle.update()
        }
        viewModel.getImage()

        binding.doodleToolBar.setNavigationOnClickListener {
            Log.d("AppTest", "setNavigationOnClickListener")
            turnOffDoodleView()
            turnOnPhotoView()
        }

        binding.doodleToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navi_download -> {
                    Log.d("AppTest", "download icon clicked")
                    val loadImageList = selectImageLoad()
                    saveImage(loadImageList)
                    true
                }
                else -> false
            }
        }
    }

    private fun setRecyclerView() {
        adapterShowImage = AdapterShowImage()
        binding.rvShowImage.adapter = adapterShowImage
        binding.rvShowImage.layoutManager = GridLayoutManager(this, 3)
        adapterDoodle = AdapterDoodle ({
            viewModel.updateCheck(it)
            binding.doodleToolBar.menu.findItem(R.id.navi_download).isVisible = true
        }, {
            viewModel.changeCheckedState(it)
        })
        binding.doodleShowImage.adapter = adapterDoodle
        binding.doodleShowImage.layoutManager = GridLayoutManager(this, 3)
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

    private fun uriToBitmap(image: String): Bitmap {
        return BitmapFactory.decodeFile(image)
    }

    private fun turnOnDoodleView() {
        binding.doodleToolBar.visibility = View.VISIBLE
        binding.doodleShowImage.visibility = View.VISIBLE
    }

    private fun turnOffPhotoView() {
        binding.rvShowImage.visibility = View.GONE
        binding.toolBar.visibility = View.GONE
    }

    private fun turnOffDoodleView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.doodleToolBar.visibility = View.GONE
        binding.doodleShowImage.visibility = View.GONE
    }

    private fun turnOnPhotoView() {
        binding.rvShowImage.visibility = View.VISIBLE
        binding.toolBar.visibility = View.VISIBLE
    }
}