package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName


data class HospitalItems (
  @SerializedName("item" ) var item : ArrayList<HospitalItem> = arrayListOf()
)