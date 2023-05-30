package com.earl.steamapi

import android.app.Application
import com.earl.steamapi.di.AppComponent
import com.earl.steamapi.di.DaggerAppComponent

class SteamApiApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }
}