package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seo.finddoc.R

class BottomFavoriteFragment : Fragment() {
    companion object{
        fun newInstance(param: String): Fragment{
            val fragment : Fragment = BottomFavoriteFragment()
            val bundle = Bundle()
            bundle.putString("title",param)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.)
        return root
    }
}