package net.ukr.vlsv.ippon_secretar.dagger.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import net.ukr.vlsv.ippon_secretar.BuildConfig
import net.ukr.vlsv.ippon_secretar.network.ApiClient
import net.ukr.vlsv.ippon_secretar.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import net.ukr.vlsv.ippon_secretar.data_base.IpponDatabase
import net.ukr.vlsv.ippon_secretar.data_base.daos.*
import net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy.ApiClientAbbyy
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.Serializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(@Named("API_1C") retrofit: Retrofit): ApiClient {
        return ApiClient(retrofit)
    }

    @Provides
    @Singleton
    fun provideApiClientAbbyy(@Named("API_ABBYY") retrofitAbbyy: Retrofit): ApiClientAbbyy {
        return ApiClientAbbyy(retrofitAbbyy)
    }

    @Provides
    @Singleton
    @Named("API_1C")
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.IPPON_API_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides
    @Singleton
    @Named("API_ABBYY")
    fun provideRetrofitAbbyy(gson: Gson, okHttpClient: OkHttpClient): Retrofit { //(sXML: JaxbConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.ABBYY_API_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(JaxbConverterFactory.create())
//                .addConverterFactory(sXML)
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
                .addInterceptor(ChuckInterceptor(application))
                .build()
    }

    @Provides
    @Singleton
        fun provideDB(context: Context): IpponDatabase {
        return Room.databaseBuilder(context.applicationContext, IpponDatabase::class.java, "ippon_DB_7.db")
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

    @Provides
    @Singleton
    fun provideCompetitionsTableDao(db: IpponDatabase): CompetitionsTableDao = db.competitionsTableDao()

    @Provides
    @Singleton
    fun hatsTableDao(db: IpponDatabase): HatsTableDao = db.hatsTableDao()

    @Provides
    @Singleton
    fun sitkaTableDao(db: IpponDatabase): SitkaTableDao = db.sitkaTableDao()
}
