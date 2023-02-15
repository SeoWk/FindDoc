package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName


data class HospitalBody (
  @SerializedName("items"      ) var items      : HospitalItems = HospitalItems(),
  @SerializedName("numOfRows"  ) var numOfRows  : Int   = -1,
  @SerializedName("pageNo"     ) var pageNo     : Int   = -1,
  @SerializedName("totalCount" ) var totalCount : Int   = -1
)