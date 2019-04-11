package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import net.ukr.vlsv.ippon_secretar.activities.LocationActivity
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.network.responses.WeatherResponse
import net.ukr.vlsv.ippon_secretar.repositories.ForecastRepository
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.ukr.vlsv.ippon_secretar.BuildConfig
import net.ukr.vlsv.ippon_secretar.activities.MainActivity
import net.ukr.vlsv.ippon_secretar.activities.ShortSettingsActivity
//import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponAPI_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefEnterUser
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@OpenClass
class MainViewModel(
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val ipponRepository_1C: IpponRepository_1C,
        private val messageUtils: MessageUtils
) : BaseViewModel(application, coroutineDispatchersProvider), LifecycleObserver {

    private val _loginData = MutableLiveData<LoginResponse>()
    val loginData: LiveData<LoginResponse> get() = _loginData

    private val _userNameAPI_1C = MutableLiveData<LoginResponse>()
    val userNameAPI_1C: LiveData<LoginResponse> get() = _userNameAPI_1C

    init {
        _userNameAPI_1C.value = ipponRepository_1C.userNameAPI_1C
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
//        updateData() виклик оновлення погоди
    }

//    private fun updateData() = launch {
//        withContext(dispatchersProvider.Main) {
//            _showSpinner.value = true
//        }
//        try {
//            val weatherResponse = forecastRepository.fetchForecast().await()
//            withContext(dispatchersProvider.Main) {
//                _weatherData.value = weatherResponse
//            }
//        } catch (e: Exception) {
//            withContext(dispatchersProvider.Main) {
//                _weatherData.value = null
//            }
//            messageUtils.showError(e)
//        }
//        withContext(dispatchersProvider.Main) {
//            _showSpinner.value = false
//        }
//    }

    //region View interaction

    @Suppress("UNUSED_PARAMETER")
    @MainThread
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                LocationActivity.REQUEST_CODE -> {
//                    updateData()
                    return true
                }
            }
        }
        return false
    }

//    @MainThread
//    fun onChangeLocationClicked() {
//        _viewAction.value = ViewAction.Navigate(LocationActivity::class.java, LocationActivity.REQUEST_CODE)
//    }
    //endregion

    @MainThread
    fun onLoginClicked(login_user_name: String, login_password: String) {
        checkLogin(login_user_name, login_password)
    }

    private fun checkLogin(login_user_name: String, login_password: String) = launch {
        withContext(dispatchersProvider.Main) {
            _showSpinner.value = true
        }

        try {
            // Check version API {100 .. 200}
            val versionAPIResponse = ipponRepository_1C.getConstant_version_Value(login_user_name, login_password).await()
            val versionAPI         = versionAPIResponse.value[0].Value
            val versionAPI_OK      = (versionAPI > 99 && (versionAPI < 200))

            if (versionAPI_OK) {
//                val androidVersion = android.os.Build.VERSION_CODES
//                val telephonyManager = ContextCompat.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//                val deviceId = telephonyManager.deviceId

                // post API user enter
                val androidVersion = ""
                val deviceId       = ""

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                val currentDate = sdf.format(Date())

                val enterResponse = ipponRepository_1C.addEnterUser(login_user_name, login_password, RefEnterUser(login_user_name, currentDate, deviceId, androidVersion)).await()

                // save user name and pass
                ipponRepository_1C.userNameAPI_1C = LoginResponse(login_user_name, login_password)
            }
            else {
                messageUtils.showError("API version not support: " + versionAPI)
            }

            withContext(dispatchersProvider.Main) {
                _viewAction.value = ViewAction.Navigate(ShortSettingsActivity::class.java, ShortSettingsActivity.REQUEST_CODE)
            }
        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {
//                _weatherData.value = null
            }
            messageUtils.showError(e)
        }

        withContext(dispatchersProvider.Main) {
            _showSpinner.value = false
        }
    }
}