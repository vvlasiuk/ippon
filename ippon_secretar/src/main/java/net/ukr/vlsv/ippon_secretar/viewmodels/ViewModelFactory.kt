package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.repositories.ForecastRepository
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class ViewModelFactory @Inject constructor(
        private val application: Application,
        private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) ->
            MainViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    forecastRepository = forecastRepository,
                    messageUtils = messageUtils
            ) as T

        modelClass.isAssignableFrom(LocationViewModel::class.java) ->
            LocationViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    forecastRepository = forecastRepository,
                    messageUtils = messageUtils
            ) as T

        else -> throw IllegalArgumentException()
    }
}