package net.ukr.vlsv.ippon_secretar.network.services

import net.ukr.vlsv.ippon_secretar.network.responses.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getCurrentWeather(@Query("q") location: String): Call<WeatherResponse>
}