package net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c


import com.google.gson.Gson
import net.ukr.vlsv.ippon_secretar.IpponApi.ConstantsResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefDeskResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefEnterUserResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefJudgesNumberResponse
//import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.ConstantsValue
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface  IpponService {
    @GET("/Fight/odata/standard.odata/Constant_version?${'$'}format=json")
    fun getConstant_version_Value(@Header("user_name") user_name: String,
                                  @Header("user_pass") user_pass: String): Call<ConstantsResponse>

    @POST("/Fight/odata/standard.odata/Catalog_enter_user?${'$'}format=json")
    fun addEnterUser(@Header("user_name") user_name: String,
                     @Header("user_pass") user_pass: String,
                     @Body refEnterUser: RefEnterUser): Call<RefEnterUserResponse>

    @GET("/Fight/odata/standard.odata/Catalog_desk?${'$'}format=json")
    fun getRefDeskList(@Header("user_name") user_name: String,
                       @Header("user_pass") user_pass: String): Call<RefDeskResponse>

    @GET("/Fight/odata/standard.odata/Catalog_judges_number?${'$'}format=json")
    fun getRefJudgesNumberList(@Header("user_name") user_name: String,
                       @Header("user_pass") user_pass: String): Call<RefJudgesNumberResponse>


}