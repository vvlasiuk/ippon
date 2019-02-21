package net.ukr.vlsv.ippon_secretar.IpponApi

import com.google.gson.annotations.SerializedName

data class ConstantsValue (
    @SerializedName("Value") val Value: List<ConstantValue>
)

data class ConstantValue(
    @SerializedName("Value") val Value: String
)
