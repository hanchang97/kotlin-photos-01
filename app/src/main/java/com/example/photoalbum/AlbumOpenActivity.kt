package com.example.photoalbum

import android.Manifest
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.photoalbum.databinding.ActivityAlbumOpenBinding
import com.google.android.material.snackbar.Snackbar

class AlbumOpenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_open)

        setAlbumOpenBtn()
    }

    private fun setAlbumOpenBtn() {
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    Log.d("AppTest", "RectangleActivity/ data : ${it.data?.data}")
                    try {
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                    contentResolver,
                                    it.data?.data!!
                                )
                            )
                        } else {
                            MediaStore.Images.Media.getBitmap(contentResolver, it.data!!.data)
                        }

                        //

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Snackbar.make(binding.root, "사진 불러오기 취소", Snackbar.LENGTH_SHORT).show()
                }
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    getContent.launch(Intent.createChooser(intent, "Gallery"))
                } else {
                    Snackbar.make(binding.root, "갤러리 접근 권한이 승인되지 않았습니다", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        binding.btnOpenAlbum.setOnClickListener {
            // 갤러리 열고 uri 가져오기 구현하기
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
