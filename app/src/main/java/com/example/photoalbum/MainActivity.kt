package com.example.photoalbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var fm: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_recycler, RecyclerFragment())
        ft.commit()

    }

    fun moveToOpenAlbumFragment() {
        fm.beginTransaction().replace(R.id.fragment_recycler, AlbumOpenFragment()).commit()
    }
}