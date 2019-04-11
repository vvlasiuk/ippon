package net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy

import com.google.gson.Gson
import net.ukr.vlsv.ippon_secretar.IpponApi.*
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Qualifier

interface AbbyyService {

    @POST("/processImage?anguage=English,Russian,Ukrainian&exportFormat=txt")
    fun processImage(): Call<ProcessImageResponse>

//    @Headers("Content-Type: application/json")
//    @POST("/restservices/processDocument?language=english,russian,Ukrainian&pagerange=1-5&gettext=true&outputformat=txt")
    @GET("/restservices/restservices/getAccountInformation")
    fun getTaskStatus(@Header("user_name") user_name: String,
                      @Header("user_pass") user_pass: String
    ): Call<ProcessImageResponse>
}