package net.ukr.vlsv.ippon_secretar.network.responses

import com.squareup.moshi.JsonClass
import net.ukr.vlsv.ippon_secretar.entities.MainWeatherInfo
import net.ukr.vlsv.ippon_secretar.entities.Weather
import net.ukr.vlsv.ippon_secretar.entities.Wind

@JsonClass(generateAdapter = true)
data class WeatherResponse(
        val id: Long,
        val main: MainWeatherInfo,
        val weather: List<Weather>,
        val wind: Wind,
        val rain: Map<String, Int>?,
        val clouds: Map<String, Int>?
)