package net.ukr.vlsv.ippon_secretar.dagger.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import net.ukr.vlsv.ippon_secretar.BuildConfig
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import net.ukr.vlsv.ippon_secretar.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import net.ukr.vlsv.ippon_secretar.data_base.IpponDatabase
import net.ukr.vlsv.ippon_secretar.data_base.daos.DeskTableDao
import net.ukr.vlsv.ippon_secretar.data_base.daos.JudgesNumberTableDao
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return ApiClient(retrofit)
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.IPPON_API_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .protocols(listOf(Protocol.HTTP_1_1))
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ChuckInterceptor(application))
                .build()
    }

    @Provides
    @Singleton
//    fun provideDB(application: Application): IpponDatabase {
        fun provideDB(context: Context): IpponDatabase {
//        return Room.databaseBuilder(context.applicationContext, IpponDatabase::class.java, "ippon.db")
        return Room.databaseBuilder(context.applicationContext, IpponDatabase::class.java, "ippon_DB_2.db")
//                .fallbackToDestructiveMigration()
//                .addCallback()
//                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideDeskTableDao(db: IpponDatabase): DeskTableDao = db.deskTableDao()

    @Provides
    @Singleton
    fun provideJudgesNumberTableDao(db: IpponDatabase): JudgesNumberTableDao = db.judgesNumberTableDao()
}
