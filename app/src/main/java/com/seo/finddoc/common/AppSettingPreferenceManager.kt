package com.seo.finddoc.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class AppSettingPreferenceManager {
    companion object{
        private lateinit var manager: AppSettingPreferenceManager
        private lateinit var sp: SharedPreferences
        private lateinit var spEditor: SharedPreferences.Editor

        fun getSettingManager(context: Context): AppSettingPreferenceManager {
            if (this::manager.isInitialized) {
                return manager
            } else {
                sp = PreferenceManager.getDefaultSharedPreferences(context)
                spEditor = sp.edit()
                manager = AppSettingPreferenceManager()
            }
            return manager
        }

        //프리퍼런스 키 값
        private const val IS_LOCATION = "is_location"
        private const val AUTO_LOGIN = "auto_login"
        private const val PUSH_NOTICE = "push_notice"
    }

    //위치 권한
    var isLocation : Boolean
        get() = sp.getBoolean(IS_LOCATION,false)
        set(permissionCheck) {
            with(spEditor) {
                putBoolean(IS_LOCATION,permissionCheck)
                apply()
            }
        }
    //로그인
    var isAutoLogin : Boolean
        get() = sp.getBoolean(AUTO_LOGIN, false)
        set(autoLogin) {
            with(spEditor) {
                putBoolean(AUTO_LOGIN, autoLogin)
                apply()
            }
        }
    //알림
    var isPushNotice: Boolean
        get() = sp.getBoolean(PUSH_NOTICE, false)
        set(pushNotice) {
            with(spEditor){
                putBoolean(PUSH_NOTICE, pushNotice)
                apply()
            }
        }
}