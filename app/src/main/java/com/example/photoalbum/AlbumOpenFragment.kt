package com.example.photoalbum

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.photoalbum.databinding.FragmentAlbumOpenBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class AlbumOpenFragment : Fragment() {

    private lateinit var binding: FragmentAlbumOpenBinding

    private val viewModel: AlbumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_album_open, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnGallery()
    }

    private fun setBtnGallery() {
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    Log.d("AppTest", "RectangleActivity/ data : ${it.data?.data}")
                    try {
                        val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, it.data?.data!!))
                        }else {
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it.data!!.data)
                        }
                        viewModel.setBitmap(bitmap)
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

        binding.btnOpenAlbum?.let {
            it.setOnClickListener {
                // 갤러리 열고 uri 가져오기 구현하기
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}