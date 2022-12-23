package com.seo.finddoc.recyclerview

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.bottom_navigation_view.BottomMainFragment
import com.seo.finddoc.databinding.SearchFragmentBinding
import com.seo.finddoc.viewmodel.SearchViewModel


class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding
    private val viewModel : SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
//        val rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        val searchBar = activity.supportActionBar
        searchBar?.let{
            it.setDisplayShowTitleEnabled(false)    //기본 제목 제거
//            it.setDisplayShowCustomEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)  //홈(뒤로가기) 버튼 생성
        }
        /**
         * 옵션 메뉴 커스텀하기 - 크기, 색상
         */
        setHasOptionsMenu(true)
        /**
         * sharedPreference 나 ViewModel로 앱 꺼질 때까지 데이터 유지할 수 있게
         */
        /*    //저장된 검색기록 가져오기
            searchHistoryList = SharedPreferenceManager.getSearchHistoryList()
            searchHistoryList.forEach {
                Log.d(ContentValues.TAG, "저장된 검색기록 - SearchedItem : ${it.keyword} , ${it.date}")
            }*/

        /*  //검색 데이터 저장 - SharedPreference
          with(binding.searchEditText) {
              val input = text.toString()

              val format = SimpleDateFormat("MM/dd",Locale.getDefault())
              val stringDate = format.format(System.currentTimeMillis())

              val newSearchData = SearchedItem(input, stringDate)
  *//*            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchHistoryList.add(newSearchData)
                    SharedPreferenceManager.storeSearchHistoryList(searchHistoryList)
                    true
                }
                false

            }*//*
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
        }*/
        //최근 검색어 리사이클러뷰
        with(binding.recentSearchesRV) {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = SearchedListAdapter(
                emptyList(),
                //개별 항목 삭제 기능
                onClickDeleteIcon = {
                    viewModel.removeData(it)
                }
            )
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        with(binding.searchEditText) {
            setOnEditorActionListener { _, actionId, _ ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchComplete()
                    handled = true
                }
                handled
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(
            viewLifecycleOwner,
            Observer {
                (binding.recentSearchesRV.adapter as SearchedListAdapter).setData(it)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_optionmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, BottomMainFragment.newInstance("메인화면"))
                    .commit()
            }
            R.id.menu_search -> {
                //최근 검색어 추가
                    searchComplete()

            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }
    /**
     * 검색된 결과로 이동하는 것까지 구현
     * 키보드 입력 바로 뜰 수 있게
     */
    private fun searchComplete(){
        var getKeyword = binding.searchEditText.text.toString()
        viewModel.addData(getKeyword.toString())
        binding.searchEditText.text = null
    }

    companion object {
        fun newInstance(subject: String): SearchFragment {
            val fragment = SearchFragment()
            with(Bundle()){
                putString("subject", subject)
                fragment.arguments = this
            }
            return fragment
        }
    }
}