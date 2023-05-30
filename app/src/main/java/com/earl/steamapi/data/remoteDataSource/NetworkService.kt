package com.earl.steamapi.data.remoteDataSource

import com.earl.steamapi.domain.models.AppNews
import com.earl.steamapi.domain.models.GameNewsResponse
import com.earl.steamapi.domain.models.SteamGameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("ISteamApps/GetAppList/v2/")
    suspend fun fetchAllSteamGames(): Response<SteamGameResponse>

    @GET("ISteamNews/GetNewsForApp/v0002/")
    suspend fun fetchNewsForApp(
        @Query("appid") appId: Int
    ): Response<GameNewsResponse>

}