package com.earl.steamapi.data.remoteDataSource

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val base_url = "https://api.steampowered.com/"

private val interceptor = HttpLoggingInterceptor()
private val gsonConverter = GsonBuilder()
    .setLenient()
    .create()
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

fun buildNetworkService(): NetworkService = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(base_url)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
    .build()
    .create(NetworkService::class.java)