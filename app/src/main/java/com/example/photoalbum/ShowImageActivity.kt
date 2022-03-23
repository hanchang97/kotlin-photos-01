package com.example.photoalbum

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.photoalbum.databinding.ActivityShowImageBinding

class ShowImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_image)


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
}