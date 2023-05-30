package com.earl.steamapi.presentation.gameNews

import android.util.Log
import androidx.lifecycle.*
import com.earl.steamapi.di.AppScope
import com.earl.steamapi.domain.Repository
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.GameNewsDetails
import com.earl.steamapi.domain.models.GameNewsResponse
import com.earl.steamapi.presentation.utils.BaseViewModel
import com.earl.steamapi.presentation.utils.CoroutinesErrorHandler
import javax.inject.Inject
import javax.inject.Provider

class GameNewsViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel() {

    private val gameNewsLiveData: MutableLiveData<SteamApiResponse<GameNewsResponse>> = MutableLiveData()

    fun fetchNewsForGame(gameId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        gameNewsLiveData,
        coroutinesErrorHandler,
    ) {
        repository.fetchNewsForApp(gameId)
    }

    fun observeGameNewsLiveData(owner: LifecycleOwner, observer: Observer<SteamApiResponse<GameNewsResponse>>) {
        gameNewsLiveData.observe(owner, observer)
    }

    fun findNewsById(id: String): GameNewsDetails? = try {
        val responseList = gameNewsLiveData.value as SteamApiResponse.Success
        responseList.data.appnews.newsitems.find { it.gid == id }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }


    @AppScope
    class Factory @Inject constructor(
        private val repository: Provider<Repository>,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == GameNewsViewModel::class.java)
            return GameNewsViewModel(repository.get()) as T
        }
    }
}