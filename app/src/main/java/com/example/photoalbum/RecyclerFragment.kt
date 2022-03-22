package com.example.photoalbum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoalbum.adapter.AdapterGridAlbum
import com.example.photoalbum.data.AlbumRectangle
import com.example.photoalbum.databinding.FragmentRecyclerBinding
import com.example.photoalbum.decoration.RecyclerDecoration

class RecyclerFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerBinding
    private lateinit var adapterGridAlbum: AdapterGridAlbum
    private lateinit var gridList: List<AlbumRectangle>

    private val viewModel: AlbumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // Log.d("AppTest", gridList.toString())

        setRecyclerView()

        viewModel.gridList.observe(viewLifecycleOwner) {
            adapterGridAlbum.submitList(it)
        }
    }

    fun setRecyclerView() {
        adapterGridAlbum = AdapterGridAlbum {
            // 앨범 오픈 프래그먼트로 이동하기를 여기서
            // viewModel 에 albumRectangle 에  리사이클러뷰에서 선택한 AlbumRectangle을 할당
            // 앨범오픈 프래그먼트에서는 이 뷰모델의 albumRectangle에 bitmap 을 넣어준다
            (activity as MainActivity).moveToOpenAlbumFragment()
        }
        binding.rvGridAlbum.adapter = adapterGridAlbum
        binding.rvGridAlbum.layoutManager = GridLayoutManager(context, 4)
        //adapterGridAlbum.submitList(gridList)
        binding.rvGridAlbum.addItemDecoration(RecyclerDecoration(30, 50, 0, 20))
    }


}