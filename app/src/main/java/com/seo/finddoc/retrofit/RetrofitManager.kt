package com.seo.finddoc.retrofit

import com.seo.finddoc.common.HospitalRestService
import com.seo.finddoc.common.TARGET_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {
        private var _hospitalService: HospitalRestService? = null
        private val hospitalService get() = _hospitalService!!
        fun getRetrofitHospitalRestService(): HospitalRestService {
            if (_hospitalService != null) {
                return hospitalService
            } else {
                //Retrofit 객체 생성 및 서비스 객체 얻기
                _hospitalService = Retrofit.Builder()
                    .baseUrl(TARGET_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(HospitalRestService::class.java)
            }
            return hospitalService
        }
    }
}