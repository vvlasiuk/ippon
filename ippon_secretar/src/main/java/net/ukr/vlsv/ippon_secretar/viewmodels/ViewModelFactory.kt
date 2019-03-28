package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Application
//import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.DataBaseRepository
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
//import net.ukr.vlsv.ippon_secretar.repositories.ForecastRepository
//import net.ukr.vlsv.ippon_secretar.shared_preferences.IpponSharedPreferences
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class ViewModelFactory @Inject constructor(
        private val application: Application,
        private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
//        private val forecastRepository: ForecastRepository,
        private val ipponRepository_1C: IpponRepository_1C,
        private val messageUtils: MessageUtils
       ,private val db_Repository: DataBaseRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) ->
            MainViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
//                    forecastRepository = forecastRepository,
                    ipponRepository_1C = ipponRepository_1C,
                    messageUtils = messageUtils
//                    sharedPreferences = sharedPreferences
            ) as T

//        modelClass.isAssignableFrom(LocationViewModel::class.java) ->
//            LocationViewModel(
//                    application = application,
//                    coroutineDispatchersProvider = coroutineDispatchersProvider,
//                    forecastRepository = forecastRepository,
//                    messageUtils = messageUtils
//            ) as T
        modelClass.isAssignableFrom(ShortSettingsViewModel::class.java) ->
            ShortSettingsViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    ipponRepository_1C = ipponRepository_1C,
                    messageUtils = messageUtils,
                    db_Repository = db_Repository
            ) as T

        else -> throw IllegalArgumentException()
    }
}