package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.RecentSearchItem
import com.seo.finddoc.databinding.SearchFragmentBinding
import com.seo.finddoc.recyclerview.RecentSearchesRecyclerViewAdapter


class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = SearchFragmentBinding.inflate(inflater, container, false)
//        val rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.searchBar)
        val searchBar = activity.supportActionBar
        searchBar?.let{
            it.setDisplayShowTitleEnabled(false)    //기본 제목 제거
//            it.setDisplayShowCustomEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)  //홈(뒤로가기) 버튼 생성
            it.setHomeAsUpIndicator(R.drawable.ic_back) //홈 기본 이미지 변경
        }

        //최근 검색어 리사이클러
        with(binding.recentSearchesRV) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = RecentSearchesRecyclerViewAdapter(recentSearchData())
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }

        return binding.root
    }

    /**
     *     삭제 예정
     */
    private fun recentSearchData() = mutableListOf<RecentSearchItem>().apply {
        add(RecentSearchItem("다나아 이비인후과","12-14"))
        add(RecentSearchItem("다나아 이비인후과","12-14"))
        add(RecentSearchItem("다나아 이비인후과","12-14"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toastMessage("뒤로가기 만들기")
            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance(subject: String?): SearchFragment {
            val fragment = SearchFragment()
            with(Bundle()){
                putString("subject", subject)
                fragment.arguments = this
            }
            return fragment
        }
    }
}