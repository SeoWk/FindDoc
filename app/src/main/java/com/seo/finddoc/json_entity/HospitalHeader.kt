package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName


data class HospitalHeader (
  @SerializedName("resultCode" ) var resultCode : String = "",
  @SerializedName("resultMsg"  ) var resultMsg  : String = ""
)