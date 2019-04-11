package net.ukr.vlsv.ippon_secretar.cache

import android.content.SharedPreferences
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class SPCache @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {

        internal const val PREF_API_1C_USER_NAME = "PREF_API_1C_USER_NAME"
        internal const val PREF_API_1C_USER_PASS = "PREF_API_1C_USER_PASS"

        internal const val PREF_API_CONNECTED    = "PREF_API_CONNECTED"
        internal const val PREF_Desk_ID          = "PREF_Desk_ID"
        internal const val PREF_JudgesNumber_ID  = "PREF_JudgesNumber_ID"
        internal const val PREF_Competitions_ID  = "PREF_Competitions_ID"
        internal const val PREF_hats_ID          = "PREF_hats_ID"

    }

    var userNameAPI_1C: LoginResponse = LoginResponse(
            sharedPreferences.getString(PREF_API_1C_USER_NAME, ""),
            sharedPreferences.getString(PREF_API_1C_USER_PASS, ""))!!
        set(value) {
            if (sharedPreferences.edit().putString(PREF_API_1C_USER_NAME, value.login_user_name).commit()) {
                if (sharedPreferences.edit().putString(PREF_API_1C_USER_PASS, value.login_password).commit()) {
                    field = value
                }
            }
        }

    var connectedAPI: Boolean = sharedPreferences.getBoolean(PREF_API_CONNECTED, false)!!
        set(value) {if (sharedPreferences.edit().putBoolean(PREF_API_CONNECTED, value).commit()) {field = value}
        }

    var desk_ID: Int = sharedPreferences.getInt(PREF_Desk_ID, 0)!!
        set(value) {if (sharedPreferences.edit().putInt(PREF_Desk_ID, value).commit()) {field = value}
        }

    var judgesNumber_ID: Int = sharedPreferences.getInt(PREF_JudgesNumber_ID, 0)!!
        set(value) {if (sharedPreferences.edit().putInt(PREF_JudgesNumber_ID, value).commit()) {field = value}
        }

    var competitions_ID: Int = sharedPreferences.getInt(PREF_Competitions_ID, 0)!!
        set(value) {if (sharedPreferences.edit().putInt(PREF_Competitions_ID, value).commit()) {field = value}
        }

    var hats_ID: Int = sharedPreferences.getInt(PREF_hats_ID, 0)!!
        set(value) {if (sharedPreferences.edit().putInt(PREF_hats_ID, value).commit()) {field = value}
        }
}
