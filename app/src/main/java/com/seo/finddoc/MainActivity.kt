package com.seo.finddoc

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seo.finddoc.bottom_navigation_view.BottomFavoriteFragment
import com.seo.finddoc.bottom_navigation_view.BottomMainFragment
import com.seo.finddoc.bottom_navigation_view.BottomMypageFragment
import com.seo.finddoc.common.AppPermissionCheck
import com.seo.finddoc.common.AppSettingPreferenceManager
import com.seo.finddoc.common.toastMessage

class MainActivity : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private val delayTime = 1500L

    companion object{
        const val LBS_CHECK_TAG = "LBS_CHECK_TAG"
        const val LBS_CHECK_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val keyHash = Utility.getKeyHash(this)
//        Log.e("Hash", keyHash)

        //BottomNavigationView
        if (savedInstanceState == null) {
            with(supportFragmentManager.beginTransaction()) {
                add(R.id.container, BottomMainFragment.newInstance("홈"))
                commitNow()
            }
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_item -> replaceFragment(BottomMainFragment.newInstance("홈"))
                R.id.favorite_item -> replaceFragment(BottomFavoriteFragment.newInstance("즐겨찾기"))
                R.id.mypage_item -> replaceFragment(BottomMypageFragment.newInstance("마이페이지"))
                else -> throw IllegalStateException("Unexpected value: " + it.itemId)
            }
            true
        }
        bottomNavigation.itemIconTintList = null


        /**
         * 스플래시 이후...중간에 끊어진 경우 종료되도록. 관찰 가능한 리스너 필요 ConnectivityManager.NetworkCallback()
         *
         */

        //네트워크 가능 여부
        if (isNetworkAvailable()) {

        } else {
            Log.e(LBS_CHECK_TAG, "네트워크에 연결되지 않음")
            toastMessage("네트워크에 연결되지 않아 사용종료됩니다")
            finish()
        }

        addOnBackPressedDispatcher {
            val currentTime = System.currentTimeMillis()
            val intervalTime = currentTime - backPressedTime

            if (intervalTime in 0..delayTime) {
                finish()
            } else {
                backPressedTime = currentTime
                toastMessage("한번 더 뒤로가기하시면 종료됩니다")
            }

        }

    }
    //네트워크 접속 확인
    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
            return when {
                //와이파이
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                //데이터 네트워크 연결시
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //IOT 장비 등
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //블루투스 인터넷
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else{
            @Suppress("DEPRECATION")
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }

    //권한 확인
    private lateinit var permissionCheck: AppPermissionCheck

    /**
     * PreferenceManager 수정하기
     */
    override fun onResume() {
        super.onResume()
        if (!AppSettingPreferenceManager.getSettingManager(this).isLocation) {
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
            AppSettingPreferenceManager.getSettingManager(this).isLocation = true
        }
    }

    private fun AppCompatActivity.addOnBackPressedDispatcher(backPressed: () -> Unit) {
        onBackPressedDispatcher.addCallback(
            this@MainActivity, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressed.invoke()
                }
            }
        )
    }

}