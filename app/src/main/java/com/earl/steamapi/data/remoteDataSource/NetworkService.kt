package com.earl.steamapi.data.remoteDataSource

import com.earl.steamapi.domain.models.SteamGameResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("ISteamApps/GetAppList/v2/")
    suspend fun fetchAllSteamGames(): Response<SteamGameResponse>

}