package com.seo.finddoc

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seo.finddoc.databinding.ActivityMainBinding
import com.seo.finddoc.fragment.BottomFavoriteFragment
import com.seo.finddoc.fragment.BottomMainFragment
import com.seo.finddoc.fragment.BottomMypageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BottomNavigationView
        if (savedInstanceState == null) {
            with(supportFragmentManager.beginTransaction()) {
                add(R.id.container, BottomMainFragment.newInstance("홈") )
                commit()
            }
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.home_item -> replaceFragment(BottomMainFragment.newInstance("홈"))
                R.id.favorite_item -> replaceFragment(BottomFavoriteFragment.newInstance("즐겨찾기"))
                R.id.mypage_item -> replaceFragment(BottomMypageFragment.newInstance("마이페이지"))
                else -> throw IllegalStateException("Unexpected value: " + it.itemId)
            }
            true
        }



    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }
}