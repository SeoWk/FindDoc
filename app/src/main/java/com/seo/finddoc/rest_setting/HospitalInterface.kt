package com.seo.finddoc.rest_setting

import com.seo.finddoc.json_entity.HospitalEntityRoot
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
interface HospitalInterface {
    @GET("B552657/HsptlAsembySearchService/getHsptlMdcncFullDown")
    fun  findHospitalInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("_type") type: String) : Call<HospitalRoot>
}*/
interface HospitalInterface {
    @GET("B551182/hospInfoServicev2/getHospBasisList")
    fun findHospitalInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("sidoCd") sidoCd: Int,
        @Query("_type") type: String
    ) : Call<HospitalEntityRoot>

    @GET("B551182/hospInfoServicev2/getHospBasisList")
    suspend fun findHospitalInfoByCoroutine(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("sidoCd") sidoCd: Int,
        @Query("_type") type: String
    ) : Response<HospitalEntityRoot>
}

