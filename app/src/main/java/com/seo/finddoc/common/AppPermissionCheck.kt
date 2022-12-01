package com.seo.finddoc.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val LOCATION_PERMISSION_REQUEST_CODE = 1000

class AppPermissionCheck(private val context: Context, private val owner: Activity) {
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val permissionList = mutableListOf<String>()
    fun currentAppCheckPermission(): Boolean{
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(context, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            return false
        }
        return true
    }
    fun currentAppRequestPermission(){
        ActivityCompat.requestPermissions(owner,permissionList.toTypedArray(),LOCATION_PERMISSION_REQUEST_CODE)
    }
    fun currentAppPermissionResult(requestCode : Int, grantResults : IntArray): Boolean {
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE && (grantResults.isNotEmpty())){
            for(result in grantResults){
                if(result == -1){
                    return false
                }
            }
        }
        return true
    }
}