package com.jihan.lib_common.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jihan.lib_common.base.MyApplication.Companion.appContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://10.0.2.2:8080/VehicleServer_war/"

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(appContext)
        )
    }

    private val client = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}