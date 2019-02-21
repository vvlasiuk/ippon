package net.ukr.vlsv.ippon_secretar.network.interceptors

import net.ukr.vlsv.ippon_secretar.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
                chain.request().newBuilder().url(
                        chain.request().url().newBuilder()
                                .addQueryParameter("appid", BuildConfig.API_KEY)
                                .build()
                ).build()
        )
    }
}