package com.earl.steamapi.di

import androidx.lifecycle.ViewModel
import com.earl.steamapi.presentation.gameNews.GameNewsFragment
import com.earl.steamapi.presentation.steamGames.SteamGamesFragment
import dagger.Component

@[AppScope Component(
    modules = [
        AppMainModule::class
    ]
)]
interface AppComponent {

    fun inject(fragment: SteamGamesFragment)

    fun inject(fragment: GameNewsFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}

internal class AppComponentViewModel : ViewModel() {

    val appComponent = DaggerAppComponent.builder().build()
}