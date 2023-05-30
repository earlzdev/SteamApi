package com.earl.steamapi.data

import android.util.Log
import com.earl.steamapi.data.remoteDataSource.NetworkService
import com.earl.steamapi.data.remoteDataSource.apiRequestFlow
import com.earl.steamapi.domain.Repository
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.SteamGameResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): Repository {

    override fun fetchAllSteamGames(): Flow<SteamApiResponse<SteamGameResponse>> = apiRequestFlow {
        networkService.fetchAllSteamGames()
    }
}