package com.xupt.ttms.util.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
object RetrofitManager {

    lateinit var context: Context

    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "token",
                context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("token", "")!!
            )
            .build()
        Log.d("TAG", ": ${context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("token", "")!!}")
        chain.proceed(request)
    }.build()

    private val okHttpClientService = OkHttpClient.Builder().addInterceptor { chain ->
        val response = chain.proceed(chain.request())
        val token = response.header("token", "")
        context.getSharedPreferences("user", Context.MODE_PRIVATE).edit().putString("token", token).apply()
        Log.d("TAG", ": ${context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("token", "")!!}")
        response
    }.build()

    private val myRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://101.201.78.192:9999/ttms/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val myRetrofitLogin: Retrofit = Retrofit.Builder()
        .baseUrl("http://101.201.78.192:9999/ttms/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClientService)
        .build()

    fun <T> createService(serviceClass:Class<T>): T = myRetrofit.create(serviceClass)
    fun <T> createLoginService(serviceClass:Class<T>): T = myRetrofitLogin.create(serviceClass)
}