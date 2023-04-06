package com.jihan.monitor.lib_common.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jihan.monitor.lib_common.base.MyApplication.Companion.appContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp

object ServiceCreator {
    private const val BASE_URL = "http://192.168.0.103:8080/VehicleServer/"

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(appContext)
        )
    }

    fun createCustomClient(interceptor: Interceptor? = null): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .cookieJar(cookieJar)

        interceptor?.let {
            clientBuilder.addInterceptor(it)
        }

        return clientBuilder.build()
    }

    fun <T> create(serviceClass: Class<T>, interceptor: Interceptor? = null): T {
        val gson =
            GsonBuilder().registerTypeAdapter(Timestamp::class.java, TimestampTypeAdapter())
                .create()
        val customClient = createCustomClient(interceptor)
        val customRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(customClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return customRetrofit.create(serviceClass)
    }

    inline fun <reified T> create(interceptor: Interceptor? = null): T = create(T::class.java, interceptor)

}