package com.earl.steamapi.di

import dagger.Component

@[AppScope Component(
    modules = [
        AppMainModule::class,
        AppMappersModule::class
    ]
)]
interface AppComponent {



    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}