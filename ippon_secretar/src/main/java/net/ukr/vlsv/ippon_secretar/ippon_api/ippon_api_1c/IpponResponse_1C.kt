package net.ukr.vlsv.ippon_secretar.IpponApi

import androidx.annotation.XmlRes
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.ConstantValue
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefDesk_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefJudgesNumber_1C

@JsonClass(generateAdapter = true)
data class ConstantsResponse (
    val value: List<ConstantValue>
)

@JsonClass(generateAdapter = true)
data class RefEnterUserResponse(
        val Description: String,
        val date: String
)

@JsonClass(generateAdapter = true)
data class RefDeskResponse(
        val value: List<RefDesk_1C>
)

@JsonClass(generateAdapter = true)
data class RefJudgesNumberResponse(
        val value: List<RefJudgesNumber_1C>
)

