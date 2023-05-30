package com.earl.steamapi.domain.models

import com.earl.steamapi.domain.utils.Same

data class SteamGameResponse(
    val applist: AppList
)

data class AppList(
    val apps: List<SteamGame>
)

data class SteamGame(
    val appid: Int,
    val name: String
): Same<SteamGame> {

    override fun same(value: SteamGame) = value == this
}
