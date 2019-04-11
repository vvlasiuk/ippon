package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Application
//import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.DataBaseRepository
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy.AbbyyRepository
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
        private val ipponRepository_1C: IpponRepository_1C,
        private val messageUtils: MessageUtils,
        private val db_Repository: DataBaseRepository,
        private val abbyyRepository: AbbyyRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) ->
            MainViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    ipponRepository_1C = ipponRepository_1C,
                    messageUtils = messageUtils
            ) as T

        modelClass.isAssignableFrom(ShortSettingsViewModel::class.java) ->
            ShortSettingsViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    ipponRepository_1C = ipponRepository_1C,
                    messageUtils = messageUtils,
                    db_Repository = db_Repository
            ) as T

        modelClass.isAssignableFrom(ListHatViewModel::class.java) ->
            ListHatViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    messageUtils = messageUtils,
                    db_Repository = db_Repository,
                    ipponRepository_1C = ipponRepository_1C
            ) as T

        modelClass.isAssignableFrom(ListHatAddViewModel::class.java) ->
            ListHatAddViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    messageUtils = messageUtils,
                    db_Repository = db_Repository,
                    ipponRepository_1C = ipponRepository_1C,
                    abbyyRepository = abbyyRepository
            ) as T

        else -> throw IllegalArgumentException()
    }
}