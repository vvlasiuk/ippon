package net.ukr.vlsv.ippon_secretar.network.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val login_user_name: String,
    val login_password: String
 )
