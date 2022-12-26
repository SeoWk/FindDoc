package com.seo.finddoc.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val LOCATION_PERMISSION_REQUEST_CODE = 1000

class AppPermissionCheck(private val context: Context, private val target: Activity) {

    //필요한 권한 정의
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
        /**
         * 필요할 경우 추가
         */
    )

    private val permissionList = mutableListOf<String>()

    //요청 전 승인 여부확인
    fun currentAppCheckPermission(): Boolean{
        for(permission in permissions){
            val result = ContextCompat.checkSelfPermission(context, permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission)
            }
        }
        if(permissionList.isNotEmpty()){
            return false
        }
        return true
    }

    //권한 요청
    fun currentAppRequestPermissions(){
        ActivityCompat.requestPermissions(target, permissionList.toTypedArray(), LOCATION_PERMISSION_REQUEST_CODE)
    }

    //요청 결과
    fun currentAppPermissionsResult(requestCode : Int, grantResults : IntArray): Boolean {
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE && (grantResults.isNotEmpty())){
            for(result in grantResults){
                if(result == -1){   //PackageManager.PERMISSION_DENIED
                    return false
                }
            }
        }
        return true
    }
}