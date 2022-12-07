package com.seo.finddoc.common

import android.content.SharedPreferences
import android.preference.PreferenceManager

//프리퍼런스 키 값
private const val IS_LOCATION = "is_location"

class AppPreferenceManager {
    companion object{
        private lateinit var manager: AppPreferenceManager
        private lateinit var sp: SharedPreferences
        private lateinit var spEditor: SharedPreferences.Editor
        fun getInstance(): AppPreferenceManager {
            if (this::manager.isInitialized) {
                return manager
            } else {
                sp = PreferenceManager.getDefaultSharedPreferences(
                    FindDocApplication.getAppInstance()
                )
                spEditor= sp.edit()
                manager = AppPreferenceManager()
            }
            return manager
        }
    }


    //앱의 권한 체크 여부
    var isPermission : Boolean
        get() = sp.getBoolean(IS_LOCATION,false)
        set(permissionCheck) {
            with(spEditor) {
                putBoolean(IS_LOCATION,permissionCheck).apply()
            }
        }

}