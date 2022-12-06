package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.databinding.BottomFavoriteFragmentBinding

class BottomMypageFragment  : Fragment(){
//    private lateinit var binding:
    companion object{
        fun newInstance(tab: String): Fragment {
            val fragment: Fragment = BottomMypageFragment()
            val bundle = Bundle()
            bundle.putString("title", tab)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toastMessage("제작중입니다")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}