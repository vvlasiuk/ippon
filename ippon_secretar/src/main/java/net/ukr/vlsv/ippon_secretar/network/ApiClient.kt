package net.ukr.vlsv.ippon_secretar.network

import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponService
import net.ukr.vlsv.ippon_secretar.network.services.WeatherService
import retrofit2.Retrofit

@OpenClass
class ApiClient(retrofit: Retrofit) {

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

    val ipoonService: IpponService = retrofit.create(IpponService::class.java)
}