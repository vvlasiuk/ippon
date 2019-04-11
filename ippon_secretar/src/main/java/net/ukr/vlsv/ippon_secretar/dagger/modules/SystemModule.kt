package net.ukr.vlsv.ippon_secretar.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import dagger.Module
import dagger.Provides
import org.simpleframework.xml.Serializer
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
class SystemModule(private val application: Application) {

    companion object {

        val moshi: Moshi = Moshi.Builder().build()

        val gson = GsonBuilder()
                .setLenient()
                .create() //SimpleXmlConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDefaultSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = moshi

    @Provides
    @Singleton
    internal fun provideGson(): Gson = gson


    @Provides
    @Singleton
    internal fun provideCoroutineDispatchersProvider() = CoroutineDispatchersProvider()
}
