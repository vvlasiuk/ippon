package net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c


import net.ukr.vlsv.ippon_secretar.IpponApi.*
//import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.ConstantsValue
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefDataFiles_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefOcrScan_1C
import retrofit2.Call
import retrofit2.http.*

//@Qualifier
interface  IpponService {
    @GET("IPPON/odata/standard.odata/Constant_version?${'$'}format=json")
    fun getConstant_version_Value(@Header("user_name") user_name: String,
                                  @Header("user_pass") user_pass: String): Call<ConstantsResponse>

    @POST("IPPON/odata/standard.odata/Catalog_enter_user?${'$'}format=json")
    fun addEnterUser(@Header("user_name") user_name: String,
                     @Header("user_pass") user_pass: String,
                     @Body refEnterUser: RefEnterUser): Call<RefEnterUserResponse>

    @GET("IPPON/odata/standard.odata/Catalog_desk?${'$'}format=json")
    fun getRefDeskList(@Header("user_name") user_name: String,
                       @Header("user_pass") user_pass: String): Call<RefDeskResponse>

    @GET("IPPON/odata/standard.odata/Catalog_judges_number?${'$'}format=json")
    fun getRefJudgesNumberList(@Header("user_name") user_name: String,
                       @Header("user_pass") user_pass: String): Call<RefJudgesNumberResponse>

    @GET("IPPON/odata/standard.odata/Catalog_competitions?${'$'}format=json")
    fun getRefCompetitionsList(@Header("user_name") user_name: String,
                               @Header("user_pass") user_pass: String): Call<RefCompetitionsResponse>

    @GET("IPPON/odata/standard.odata/Catalog_hats?${'$'}format=json")
    fun getRefHatsList(@Header("user_name") user_name: String,
                       @Header("user_pass") user_pass: String): Call<RefHatsResponse>

    @POST("IPPON/odata/standard.odata/Catalog_dataFiles?${'$'}format=json")
    @Headers("Content-Type: image/jpeg")
    fun addFile(@Header("user_name") user_name: String,
                @Header("user_pass") user_pass: String,
                @Body refDataFiles_1C: RefDataFiles_1C): Call<RefDataFilesResponse>

    @POST("IPPON/odata/standard.odata/Catalog_ocr_scan?${'$'}format=json")
//    @Headers("Content-Type: image/jpeg")
    fun ocr_scan(@Header("user_name") user_name: String,
                 @Header("user_pass") user_pass: String,
                 @Body refOcrScan_1C: RefOcrScan_1C): Call<RefOcrScanResponse>
}