package com.earl.steamapi.data.remoteDataSource

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val base_url = ""

fun createGson(): Gson = GsonBuilder().create()

fun buildNetworkService(): NetworkService = Retrofit.Builder()
    .baseUrl(base_url)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(createGson()))
    .build()
    .create(NetworkService::class.java)