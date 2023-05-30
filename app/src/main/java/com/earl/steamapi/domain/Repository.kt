package com.earl.steamapi.domain

import com.earl.steamapi.domain.models.SteamGameResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchAllSteamGames(): Flow<SteamApiResponse<SteamGameResponse>>
}