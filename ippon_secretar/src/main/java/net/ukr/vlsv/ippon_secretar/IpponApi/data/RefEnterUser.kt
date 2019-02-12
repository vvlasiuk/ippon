package net.ukr.vlsv.ippon_secretar.IpponApi.data

import com.google.gson.annotations.SerializedName

data class RefEnterUserList(
    @SerializedName("value") val value: List<RefEnterUser>
)

data class RefEnterUser(
    @SerializedName("Description") val Description: String,
    @SerializedName("date") val date: String
)