package com.seo.finddoc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seo.finddoc.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearchBinding.inflate(inflater, container, false)

        //툴바
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        val toolbar = activity.supportActionBar
        toolbar?.let{
            it.setDisplayShowTitleEnabled(false) //기본 제목 제거
            it.setDisplayHomeAsUpEnabled(true)  //홈(뒤로가기) 버튼 생성
            it.setHomeAsUpIndicator(R.drawable.ic_back) //홈 기본 이미지 변경
        }

        //검색창
        val searchView = binding.searchBar


        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
            }
    }
}