package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.MainThread
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

@OpenClass
class MainViewModel(
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : BaseViewModel(application, coroutineDispatchersProvider), LifecycleObserver {

    //region LiveData

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData
    //endregion

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {


        updateData()
    }

    private fun updateData() = launch {
        withContext(dispatchersProvider.Main) {
            _showSpinner.value = true
        }
        try {
            val weatherResponse = forecastRepository.fetchForecast().await()
            withContext(dispatchersProvider.Main) {
                _weatherData.value = weatherResponse
            }
        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {
                _weatherData.value = null
            }
            messageUtils.showError(e)
        }
        withContext(dispatchersProvider.Main) {
            _showSpinner.value = false
        }
    }

    //region View interaction

    @Suppress("UNUSED_PARAMETER")
    @MainThread
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                LocationActivity.REQUEST_CODE -> {
                    updateData()
                    return true
                }
            }
        }
        return false
    }

    @MainThread
    fun onChangeLocationClicked() {
        _viewAction.value = ViewAction.Navigate(LocationActivity::class.java, LocationActivity.REQUEST_CODE)
    }
    //endregion
}