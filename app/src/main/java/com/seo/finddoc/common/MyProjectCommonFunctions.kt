package com.seo.finddoc.common

import android.widget.Toast

fun toastMessage(message: String) {
    Toast.makeText(FindDocApplication.getAppInstance(),message,Toast.LENGTH_SHORT).show()
}
