package com.seo.finddoc.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk
import com.seo.finddoc.BuildConfig
import com.seo.finddoc.etc_room.SearchRepository
import com.seo.finddoc.etc_room.SearchRoomDatabase

/**
 *  앱 런처아이콘을 터치하면 처음 실행되는 코드
 *  App Scope 모든 코틀린 클래스/파일에서 호출할 수 있는 코드
 */
class FindDocApplication : Application(){
    //    val applicationScope = CoroutineScope(SupervisorJob())
    private val searchDatabase by lazy { SearchRoomDatabase.getDatabase(this) }
    val searchRepository by lazy { SearchRepository(searchDatabase.searchDao()) }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        settingScreenPortrait()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "{${BuildConfig.KAKAO_API_KEY}")
    }
    companion object{
        private lateinit var appInstance: FindDocApplication
        fun getAppInstance() = appInstance
    }

    private fun settingScreenPortrait(){
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}