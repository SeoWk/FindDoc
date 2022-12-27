package com.seo.finddoc.json_data

import com.google.gson.annotations.SerializedName

data class HospitalEntity(
    @SerializedName("ykiho") var code : String  //암호화된 요양기호
)
