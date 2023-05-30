package com.earl.steamapi.di

import com.earl.steamapi.data.RepositoryImpl
import com.earl.steamapi.data.remoteDataSource.NetworkService
import com.earl.steamapi.data.remoteDataSource.buildNetworkService
import com.earl.steamapi.domain.Repository
import dagger.Module
import dagger.Provides

@Module
class AppMainModule {

    @Provides
    fun provideRepository(
        networkService: NetworkService
    ): Repository = RepositoryImpl(
        networkService
    )

    @Provides
    fun networkService(): NetworkService = buildNetworkService()
}