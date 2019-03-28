package net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data

import com.google.gson.annotations.SerializedName

//data class RefEnterUserList(
//        @SerializedName("value") val value: List<RefEnterUser>
//)

data class RefEnterUser(
        val Description: String?,
        val date: String?,
        val hash_IMEI: String?,
        val android_version: String?
)