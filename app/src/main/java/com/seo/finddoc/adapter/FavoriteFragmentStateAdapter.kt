package com.seo.finddoc.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoriteFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = arrayListOf<Fragment>()
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun appendFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}