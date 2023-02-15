package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName

 data class HospitalEntityRoot (
  @SerializedName("response" ) var response : HospitalResponse = HospitalResponse()
)
