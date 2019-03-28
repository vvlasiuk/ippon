package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Activity
import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.repositories.ForecastRepository
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils

class LocationViewModel(
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : BaseViewModel(application, coroutineDispatchersProvider) {

    //region LiveData

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location
    //endregion

    init {
        //TODO: move this to onCreate()
//        _location.value = forecastRepository.location
    }

    //region View interaction

    @MainThread
    fun onSaveClicked(location: String) {
        if (location.isEmpty()) {
            messageUtils.showToast(
                    getApplication<Application>().getString(R.string.error_empty_location)
            )
        } else {
//            forecastRepository.location = location
            _viewAction.value = ViewAction.Finish(Activity.RESULT_OK)
        }
    }
    //endregion
}