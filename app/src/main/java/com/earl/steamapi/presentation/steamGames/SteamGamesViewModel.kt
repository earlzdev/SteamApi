package com.earl.steamapi.presentation.steamGames

import androidx.lifecycle.*
import com.earl.steamapi.di.AppScope
import com.earl.steamapi.domain.Repository
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.AppList
import com.earl.steamapi.domain.models.SteamGameResponse
import com.earl.steamapi.presentation.utils.BaseViewModel
import com.earl.steamapi.presentation.utils.CoroutinesErrorHandler
import javax.inject.Inject
import javax.inject.Provider

class SteamGamesViewModel(
    private val repository: Repository
): BaseViewModel() {

    private val steamGamesLiveData: MutableLiveData<SteamApiResponse<SteamGameResponse>> = MutableLiveData()

    fun getSteamGames(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        steamGamesLiveData,
        coroutinesErrorHandler,
    ) {
        repository.fetchAllSteamGames()
    }

    fun observeSteamGameLiveData(owner: LifecycleOwner, observer: Observer<SteamApiResponse<SteamGameResponse>>) {
        steamGamesLiveData.observe(owner, observer)
    }

    @AppScope
    class Factory @Inject constructor(
        private val repository: Provider<Repository>,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SteamGamesViewModel::class.java)
            return SteamGamesViewModel(repository.get()) as T
        }
    }
}