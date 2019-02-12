package net.ukr.vlsv.ippon_secretar.IpponApi

import net.ukr.vlsv.ippon_secretar.IpponApi.data.ConstantsValue
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import net.ukr.vlsv.ippon_secretar.service.IpponService
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
//import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class IpponRepository {
    val okHttpClient = OkHttpClient().newBuilder().addInterceptor(object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                .header("Authorization", Credentials.basic("admin", "1"))
            val newRequest = builder.build()
            return chain.proceed(newRequest)
        }
    }).build()

    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.github.com")
//        .baseUrl("http://192.168.0.107/Fight/odata/standard.odata")
        .baseUrl("http://192.168.0.107") ///Fight/odata/standard.odata
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val ipponService = retrofit.create(IpponService::class.java)

    fun getConstantValue(name: String): Call<ConstantsValue> {
        return ipponService.getConstantValue(name)
    }

    fun addEnterUser(name: String, date: Int) {
//        return ipponService.addEnterUser(name, date)
    }

}