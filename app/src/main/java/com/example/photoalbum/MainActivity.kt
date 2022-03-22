package com.example.photoalbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var fm: FragmentManager
    private val viewModel : AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_recycler, RecyclerFragment())
        ft.commit()

        viewModel.initList() // 리사이클러뷰에 사용할 리스트 초기화
    }

    fun moveToOpenAlbumFragment() {
        fm.beginTransaction().replace(R.id.fragment_recycler, AlbumOpenFragment()).commit()
    }
}