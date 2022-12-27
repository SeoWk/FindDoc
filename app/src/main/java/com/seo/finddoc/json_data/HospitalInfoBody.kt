package com.seo.finddoc.json_data

data class HospitalInfoBody(
    val items: List<HospitalItems>,
    var numOfRows : Int,
    var pageNo: Int,
    var totalCount: Int
)
