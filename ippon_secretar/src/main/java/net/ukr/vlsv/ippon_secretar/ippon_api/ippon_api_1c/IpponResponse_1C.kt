package net.ukr.vlsv.ippon_secretar.IpponApi

import androidx.annotation.XmlRes
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.*

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

@JsonClass(generateAdapter = true)
data class RefCompetitionsResponse(
        val value: List<RefCompetitions_1C>
)

@JsonClass(generateAdapter = true)
data class RefHatsResponse(
        val value: List<RefHats_1C>
)

@JsonClass(generateAdapter = true)
data class RefDataFilesResponse(
        val Description: String,
        val Ref_Key: String
)

@JsonClass(generateAdapter = true)
data class RefOcrScanResponse(
        val Description: String,
        val ok: String,
        val sitka: List<RefSitkaOcrScan>
)
