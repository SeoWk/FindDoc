package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.seo.finddoc.MainActivity
import com.seo.finddoc.adapter.FavoriteFragmentStateAdapter
import com.seo.finddoc.databinding.BottomFavoriteFragmentBinding

class BottomFavoriteFragment : Fragment() {
    private lateinit var binding: BottomFavoriteFragmentBinding
    private var tabItems = listOf("병원", "약국")
    companion object{
        fun newInstance(title: String): Fragment {
            val fragment: Fragment = BottomFavoriteFragment()
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
        binding = BottomFavoriteFragmentBinding.inflate(inflater,container,false)

        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        val toolbar = activity.supportActionBar
        toolbar?.let{
            it.setDisplayShowTitleEnabled(false)
//            it.title = "즐겨찾기"
        }

        //뷰페이저
        val viewpager = binding.favoriteVP
        val tab = binding.favoriteTabs

        tab.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> ""
                    1 -> ""
                    else -> throw IllegalStateException("Unexpected value: " + tab.position)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val pagerAdapter = FavoriteFragmentStateAdapter(this)
        pagerAdapter.run{
            appendFragment(FavoriteViewPagerFragment.newInstance(tabItems[0]))
            appendFragment(FavoriteViewPagerFragment.newInstance(tabItems[1]))
        }
        viewpager.adapter = pagerAdapter

        TabLayoutMediator(tab, viewpager, TabLayoutMediator.TabConfigurationStrategy{
            tab, position -> tab.text = tabItems[position]
        }).attach()

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}
