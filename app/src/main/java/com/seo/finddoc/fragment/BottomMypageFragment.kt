package com.seo.finddoc.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.databinding.BottomMypageFragmentBinding

class BottomMypageFragment  : Fragment(){
    private lateinit var binding: BottomMypageFragmentBinding

    companion object{
        fun newInstance(title: String): Fragment {
            val fragment: Fragment = BottomMypageFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomMypageFragmentBinding.inflate(inflater,container,false)


        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        val toolbar = activity.supportActionBar
        toolbar?.let{
            it.setDisplayShowTitleEnabled(false)    //기본 제목 제거
//            it.setDisplayShowCustomEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)  //홈(뒤로가기) 버튼 생성
            it.setHomeAsUpIndicator(R.drawable.ic_bell) //홈 기본 이미지 변경
        }

        //옵션 메뉴
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
        inflater.inflate(R.menu.menu_mypage_optionmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toastMessage("알림 페이지 만들기")
            }
            R.id.menu_settings -> {
                toastMessage("설정 페이지 만들기")
            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }

}