package net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy

import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponService
import net.ukr.vlsv.ippon_secretar.network.services.WeatherService
import net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy.AbbyyService
import retrofit2.Retrofit
import javax.inject.Named

@OpenClass
class ApiClientAbbyy(@Named("API_ABBYY") retrofitAbbyy: Retrofit) {

    val abbyyService: AbbyyService = retrofitAbbyy.create(AbbyyService::class.java)

}
