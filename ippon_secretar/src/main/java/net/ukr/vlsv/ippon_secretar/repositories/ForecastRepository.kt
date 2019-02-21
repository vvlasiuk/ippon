package net.ukr.vlsv.ippon_secretar.repositories

import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.extensions.await
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import net.ukr.vlsv.ippon_secretar.network.responses.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@OpenClass
@Singleton
class ForecastRepository @Inject constructor(
        private val apiClient: ApiClient,
        private val dispatchersProvider: CoroutineDispatchersProvider,
        private val spCache: SPCache
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchersProvider.IO

    var location
        get() = spCache.location
        set(value) {
            spCache.location = value
        }

    fun fetchForecast(): Deferred<WeatherResponse> = async {
        apiClient.weatherService.getCurrentWeather(location).await()
    }
}
