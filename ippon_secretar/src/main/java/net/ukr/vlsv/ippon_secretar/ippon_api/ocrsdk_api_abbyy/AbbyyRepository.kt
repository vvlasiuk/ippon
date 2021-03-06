package net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy

import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import net.ukr.vlsv.ippon_secretar.IpponApi.*
import net.ukr.vlsv.ippon_secretar.extensions.await
//import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
//import net.ukr.vlsv.ippon_secretar.network.responses.WeatherResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@OpenClass
@Singleton
class AbbyyRepository @Inject constructor(
        private val apiClient: ApiClientAbbyy,
        private val dispatchersProvider: CoroutineDispatchersProvider,
        private val spCache: SPCache
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchersProvider.IO

//    var userAPI
//        get() = LoginResponse("karate_ippon", "bugaga84")//spCache.userNameAPI_1C
//        set(value) {
//            spCache.userNameAPI_1C = value
//        }

    fun processImage(login_user_name: String, login_password: String): Deferred<ProcessImageResponse> = async {
        apiClient.abbyyService.processImage().await()
    }

    fun getTaskStatus(taskId: String): Deferred<ProcessImageResponse> = async {
//        apiClient.abbyyService.getTaskStatus("karate_ippon", "PTIcUduhk2hgNmd8nBhr3JPl", taskId).await()
        apiClient.abbyyService.getTaskStatus("vlsv", "6627B168-A599-46B3-956F-384ED686F807").await()
    }

//    fun addEnterUser(login_user_name: String, login_password: String, refEnterUser: RefEnterUser): Deferred<RefEnterUserResponse> = async {
//        apiClient.ipoonService.addEnterUser(
//                login_user_name,
//                login_password,
//                refEnterUser
//        ).await()
//    }
//
//    fun getRefDeskList(login_user_name: String, login_password: String): Deferred<RefDeskResponse> = async {
//        apiClient.ipoonService.getRefDeskList(
//                login_user_name,
//                login_password
//        ).await()
//    }
//
//    fun getRefJudgesNumberList(login_user_name: String, login_password: String): Deferred<RefJudgesNumberResponse> = async {
//        apiClient.ipoonService.getRefJudgesNumberList(
//                login_user_name,
//                login_password
//        ).await()
//    }
//
//    fun getRefCompetitionsList(login_user_name: String, login_password: String): Deferred<RefCompetitionsResponse> = async {
//        apiClient.ipoonService.getRefCompetitionsList(
//                login_user_name,
//                login_password
//        ).await()
//    }
//
//    fun getRefHatsList(login_user_name: String, login_password: String): Deferred<RefHatsResponse> = async {
//        apiClient.ipoonService.getRefHatsList(
//                login_user_name,
//                login_password
//        ).await()
//    }
}
