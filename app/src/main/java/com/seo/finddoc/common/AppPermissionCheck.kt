package com.seo.finddoc.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val LOCATION_PERMISSION_REQUEST_CODE = 1000

class AppPermissionCheck(private val context: Context, private val owner: Activity) {

}