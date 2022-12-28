package com.seo.finddoc.json_data

import com.google.gson.annotations.SerializedName

data class HospitalItems(
    @SerializedName("item") val item: HospitalEntity
)
