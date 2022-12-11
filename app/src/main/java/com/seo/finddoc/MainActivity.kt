package com.seo.finddoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seo.finddoc.common.AppPermissionCheck
import com.seo.finddoc.common.AppPreferenceManager
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

    //권한 부여
    private lateinit var permissionCheck: AppPermissionCheck

    override fun onResume() {
        super.onResume()
        if (!AppPreferenceManager.getInstance().isPermission) {
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
            AppPreferenceManager.getInstance().isPermission = true
        }
    }
}