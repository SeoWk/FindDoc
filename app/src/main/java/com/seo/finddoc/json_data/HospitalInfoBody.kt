package com.seo.finddoc.json_data

import com.google.gson.annotations.SerializedName

data class HospitalInfoBody(
    @SerializedName("items") val items: HospitalItems,
    @SerializedName("numOfRows") var numOfRows : Int,
    @SerializedName("pageNo") var pageNo: Int,
    @SerializedName("totalCount") var totalCount: Int
)
