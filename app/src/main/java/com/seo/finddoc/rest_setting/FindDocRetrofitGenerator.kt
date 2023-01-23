package com.seo.finddoc.rest_setting

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val HOSPITAL_ADDRESS = "https://apis.data.go.kr/"
const val DATA_API_KEY = "8djpaTp6a76OcOMhH48u9FU0hmp%2BL6JNyOqMRWypPjmYG1IHk9kOt5RjZ%2FGoQ3C8x62b3FOU1Ju5EY9uuT5VYg%3D%3D"
class FindDocRetrofitGenerator {
    companion object{
        private var hospitalInterface : HospitalInterface? = null
        fun generateHospitalInstance(): HospitalInterface {
            if (hospitalInterface == null) {
                val gson : Gson = GsonBuilder()
//                    .registerTypeAdapter()
                    .setLenient()
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl(HOSPITAL_ADDRESS)
                    .client(getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                hospitalInterface = retrofit.create(HospitalInterface::class.java)
            }
            return  hospitalInterface!!
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {

                }
                override fun checkServerTrusted(
                    chain: Array<out java.security.cert.X509Certificate>?,
                    authType: String?
                ) {

                }
                override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate>? {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

            return builder.build()
        }
    }
}