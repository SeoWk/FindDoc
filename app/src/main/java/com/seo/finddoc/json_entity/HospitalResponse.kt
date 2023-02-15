package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName


data class HospitalResponse (
  @SerializedName("header" ) var header : HospitalHeader = HospitalHeader(),
  @SerializedName("body"   ) var body   : HospitalBody   = HospitalBody()
)