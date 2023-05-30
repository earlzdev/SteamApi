package com.earl.steamapi.domain

sealed class SteamApiResponse<out T> {

    object Loading: SteamApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): SteamApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int,
    ): SteamApiResponse<Nothing>()
}