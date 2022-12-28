package com.seo.finddoc.network

import com.seo.finddoc.BuildConfig
import com.seo.finddoc.json_data.HospitalInfoBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalRestService {
    @GET("hospInfoServicev2/getHospBasisList")
    fun getHospitalCode(
//        @Path("ctg") ctg: String,
//        @Path("sec") sec: String,
        @Query("serviceKey") key: String = BuildConfig.DATA_API_KEY,
        @Query("_type") type: String,
        @Query("numOfRows") num: Int
    ): Call<HospitalInfoBody>
}