package com.earl.steamapi.presentation.steamGames

import androidx.lifecycle.*
import com.earl.steamapi.di.AppScope
import com.earl.steamapi.domain.Repository
import com.earl.steamapi.domain.SteamApiResponse
import com.earl.steamapi.domain.models.AppList
import com.earl.steamapi.domain.models.SteamGame
import com.earl.steamapi.domain.models.SteamGameResponse
import com.earl.steamapi.presentation.utils.BaseViewModel
import com.earl.steamapi.presentation.utils.CoroutinesErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class SteamGamesViewModel(
    private val repository: Repository
): BaseViewModel() {

    private val _steamGamesStateFlow: MutableStateFlow<SteamApiResponse<SteamGameResponse>> = MutableStateFlow(SteamApiResponse.Loading)
    val steamGamesStateFlow: StateFlow<SteamApiResponse<SteamGameResponse>> = _steamGamesStateFlow.asStateFlow()

    private var lastGamesList: List<SteamGame> = mutableListOf()

    fun getSteamGames(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequestWithFlow(
        _steamGamesStateFlow,
        coroutinesErrorHandler
    ) {
        repository.fetchAllSteamGames()
    }

    fun refreshList(refreshedCallback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _steamGamesStateFlow.emitAll(repository.fetchAllSteamGames())
        }
        refreshedCallback()
    }

    fun searchGamesByEnteredText(text: String) {
        try {
            val list = _steamGamesStateFlow.value as SteamApiResponse.Success
            if (text.isNotBlank()) {
                lastGamesList = list.data.applist.apps
                val newList = list.data.applist.apps.filter {
                    it.appid.toString().contains(text) || it.name.contains(text)
                }
                _steamGamesStateFlow.value = SteamApiResponse.Success(SteamGameResponse(AppList(newList)))
            } else {
                _steamGamesStateFlow.value = SteamApiResponse.Success(SteamGameResponse(AppList(lastGamesList)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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