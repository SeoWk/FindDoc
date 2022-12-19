package com.seo.finddoc.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.adapter.SearchedListAdapter
import com.seo.finddoc.common.SharedPreferenceManager
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.SearchedItem
import com.seo.finddoc.databinding.SearchFragmentBinding
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding
    //검색 기록
    private var searchHistoryList = ArrayList<SearchedItem>()
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

        //저장된 검색기록 가져오기
        searchHistoryList = SharedPreferenceManager.getSearchHistoryList()
        searchHistoryList.forEach {
            Log.d(ContentValues.TAG, "저장된 검색기록 - SearchedItem : ${it.keyword} , ${it.date}")
        }

        //검색 데이터 저장
        with(binding.searchEditText) {
            val input = text.toString()

            val format = SimpleDateFormat("MM/dd",Locale.getDefault())
            val stringDate = format.format(System.currentTimeMillis())

            val newSearchData = SearchedItem(input, stringDate)
/*            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchHistoryList.add(newSearchData)
                    SharedPreferenceManager.storeSearchHistoryList(searchHistoryList)
                    true
                }
                false
            }*/
            setOnEditorActionListener { v, actionId, event ->
                //키보드의 완료 눌렀을 때
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //리스트에 추가시키고
                    searchHistoryList.apply {
                        add(0,newSearchData)
                    }
                    //최근 검색어 목록에 저장
                    SharedPreferenceManager.storeSearchHistoryList(searchHistoryList)
                }
                false   //키패드 닫힘
            }
        }

        //최근 검색어 리사이클러
        with(binding.recentSearchesRV) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = SearchedListAdapter(searchHistoryList)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        adapter.notifyItemRangeChanged(searchHistoryList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private lateinit var adapter: SearchedListAdapter

    /**
     *     삭제 예정
     */
    private fun recentSearchData() = mutableListOf<SearchedItem>().apply {
        add(SearchedItem("다나아 이비인후과","12-14"))
        add(SearchedItem("다나아 이비인후과","12-14"))
        add(SearchedItem("다나아 이비인후과","12-14"))
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