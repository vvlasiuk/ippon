package net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c

import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import net.ukr.vlsv.ippon_secretar.IpponApi.ConstantsResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefDeskResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefEnterUserResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefJudgesNumberResponse
import net.ukr.vlsv.ippon_secretar.extensions.await
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import net.ukr.vlsv.ippon_secretar.network.responses.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@OpenClass
@Singleton
class IpponRepository_1C @Inject constructor(
        private val apiClient: ApiClient,
        private val dispatchersProvider: CoroutineDispatchersProvider,
        private val spCache: SPCache
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchersProvider.IO

    var userNameAPI_1C
        get() = spCache.userNameAPI_1C
        set(value) {
            spCache.userNameAPI_1C = value
        }

    fun getConstant_version_Value(login_user_name: String, login_password: String): Deferred<ConstantsResponse> = async {
        apiClient.ipoonService.getConstant_version_Value(
                login_user_name,
                login_password
        ).await()
    }

    fun addEnterUser(login_user_name: String, login_password: String, refEnterUser: RefEnterUser): Deferred<RefEnterUserResponse> = async {
        apiClient.ipoonService.addEnterUser(
                login_user_name,
                login_password,
                refEnterUser
        ).await()
    }

    fun getRefDeskList(login_user_name: String, login_password: String): Deferred<RefDeskResponse> = async {
        apiClient.ipoonService.getRefDeskList(
                login_user_name,
                login_password
        ).await()
    }

    fun getRefJudgesNumberList(login_user_name: String, login_password: String): Deferred<RefJudgesNumberResponse> = async {
        apiClient.ipoonService.getRefJudgesNumberList(
                login_user_name,
                login_password
        ).await()
    }
}

