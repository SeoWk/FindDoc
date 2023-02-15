package com.seo.finddoc.recyclerview

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.bottom_navigation_view.BottomMainFragment
import com.seo.finddoc.common.FindDocApplication
import com.seo.finddoc.databinding.SearchFragmentBinding
import com.seo.finddoc.room.SearchViewModel
import com.seo.finddoc.room.SearchViewModelFactory
import com.seo.finddoc.room.SearchWord

/**
 * chip 여러번 클릭시 오류
 */
class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel : SearchViewModel by viewModels {
        SearchViewModelFactory((activity?.application as FindDocApplication).searchRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)

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

        with(binding) {
            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                val checkedChipText = group.findViewById<Chip>(checkedId).text.toString()
                searchComplete(checkedChipText)
            }
        }

        //최근 검색어 리사이클러뷰
        val searchAdapter = SearchAdapter()

        with(binding.recentSearchesRV) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        searchViewModel.allWords.observe(
            viewLifecycleOwner,
            Observer { words ->
                words?.let {   searchAdapter.submitList(it) }
            }
        )

        searchAdapter.setOnItemClickListener( object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(view: View, item: SearchWord, position: Int) {
                searchComplete(item.word.toString())
                searchViewModel.deleteSearchWord(item)
            }

            override fun onDeleteClick(view: View, item: SearchWord, position: Int) {
                searchViewModel.deleteSearchWord(item)
            }

        })


        //EditText 엔터 키 이벤트
        with(binding.searchEditText) {
            setOnEditorActionListener { _, actionId, _ ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchComplete(text.toString())
                    handled = true
                }
                handled
            }
        }

        return binding.root
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
                val getKeyword = binding.searchEditText.text.toString()
                searchComplete(getKeyword)

            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }
    /**
     * 검색된 결과로 이동하는 것까지 구현
     * 키보드 입력 바로 뜰 수 있게
     */
    //검색 결과 최근 검색어에 추가
    private fun searchComplete(getKeyword: String){
        if ( getKeyword.trim().isNotEmpty()){
            val searchWord = SearchWord(getKeyword)
            searchViewModel.insertSearchWord(searchWord)
            binding.searchEditText.text = null
        }
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