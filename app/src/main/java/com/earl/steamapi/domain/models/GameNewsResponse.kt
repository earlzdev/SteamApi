package com.earl.steamapi.domain.models

import com.earl.steamapi.domain.utils.Same
import com.google.gson.annotations.SerializedName

data class GameNewsResponse(
    val appnews: AppNews
)

data class AppNews(
    val appid: Int,
    val newsitems: List<GameNewsDetails>
)

data class GameNewsDetails(
    val gid: String,
    val title: String,
    val url: String,
    @SerializedName("is_external_url") val isExternalUrl: Boolean,
    val author: String,
    val contents: String,
    @SerializedName("feedlabel") val feedLabel: String,
    val date: Int,
    @SerializedName("feedname") val feedName: String,
    @SerializedName("feed_type") val feedType: String,
    val appid: Int,
    val tags: List<String>?
): Same<GameNewsDetails> {

    override fun same(value: GameNewsDetails) = this == value
}