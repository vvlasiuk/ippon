package net.ukr.vlsv.ippon_secretar.network.interceptors

import net.ukr.vlsv.ippon_secretar.BuildConfig
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor() : Interceptor {
//class AuthInterceptor(loginResponse: LoginResponse) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val userName = originalRequest.header("user_name")
        val userPass = originalRequest.header("user_pass")
//        val userName = loginResponse.login_user_name
//        val userPass = loginResponse.login_password

//        val builder = originalRequest.newBuilder().header("Authorization", Credentials.basic("admin", "1"))
        val builder = originalRequest.newBuilder().header("Authorization", Credentials.basic(userName, userPass))
        val newRequest = builder.build()
        return chain.proceed(newRequest)
//        return chain.proceed(
//                chain.request().newBuilder()
//                        .header("Authorization", Credentials.basic("admin", "1"))
////                        .url(
////                        chain.request()
////                                .url().newBuilder()
////                                .username("admin")
////                                .password("1")
////                                .addQueryParameter("appid", BuildConfig.API_KEY)
////                                .build()
//                ).build()
////        )
//    }
    }
}