package com.example.photoalbum

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.photoalbum.adapter.AdapterDoodle
import com.example.photoalbum.adapter.AdapterShowImage
import com.example.photoalbum.data.Json
import com.example.photoalbum.data.ShowImage
import com.example.photoalbum.databinding.ActivityShowImageBinding
import com.example.photoalbum.decoration.RecyclerDecoration
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class ShowImageActivity : AppCompatActivity() {

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


        CoroutineScope(Job() + Dispatchers.IO).launch {
            val jsonString = downloadJson()
            val jsonList = jsonObjectList(jsonString)
            adapterDoodle.submitList(jsonList)
        }

        binding.doodleToolBar.setNavigationOnClickListener {
            Log.d("AppTest", "setNavigationOnClickListener")
            turnOffDoodleView()
            turnOnPhotoView()
        }

        binding.doodleToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navi_download -> {
                    Log.d("AppTest", "download icon clicked")

                    true
                }

                else -> false
            }
        }
    }



    private fun jsonObjectList(jsonString: String): List<Json> {
        val jsonArray = JSONArray(jsonString)
        val jsonList = mutableListOf<Json>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonList.add(
                Json(
                    jsonObject.getString("title"),
                    jsonObject.getString("image"),
                    jsonObject.getString("date"),
                )
            )
        }
        return jsonList
    }

    private fun setRecyclerView() {
        adapterShowImage = AdapterShowImage()
        binding.rvShowImage.adapter = adapterShowImage
        binding.rvShowImage.layoutManager = GridLayoutManager(this, 3)
        adapterDoodle = AdapterDoodle()
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

    private fun downloadJson(): String {
        val loadJson = URL("https://public.codesquad.kr/jk/doodle.json").openStream()
        val reader = BufferedReader(InputStreamReader(loadJson, "UTF-8"))
        val buffer = StringBuffer()
        var whileSwitch = true
        Log.d("AppTest", "downloading")
        while (whileSwitch) {
            val json = reader.readLine()
            if (json != null) {
                whileSwitch = false
            }
            buffer.append(json)
            Log.d("AppTest", "${buffer}")
        }
        return """$buffer""".trimIndent()
    }
}