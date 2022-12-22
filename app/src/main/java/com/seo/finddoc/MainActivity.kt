package com.seo.finddoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seo.finddoc.bottom_navigation_view.BottomFavoriteFragment
import com.seo.finddoc.bottom_navigation_view.BottomMainFragment
import com.seo.finddoc.bottom_navigation_view.BottomMypageFragment
import com.seo.finddoc.common.AppPermissionCheck
import com.seo.finddoc.common.AppSettingPreferenceManager

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
        bottomNavigation.itemIconTintList = null

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }

    //권한 부여
    private lateinit var permissionCheck: AppPermissionCheck

    override fun onResume() {
        super.onResume()
        if (!AppSettingPreferenceManager.getInstance(this).isLocation) {
            permissionCheck()
        }

    }
    private fun permissionCheck() {
        permissionCheck = AppPermissionCheck(applicationContext, this)
        if (!permissionCheck.currentAppCheckPermission()) {
            permissionCheck.currentAppRequestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!permissionCheck.currentAppPermissionsResult(requestCode,grantResults)) {
            permissionCheck.currentAppRequestPermissions()
        }else{
            AppSettingPreferenceManager.getInstance(this).isLocation = true
        }
    }
}