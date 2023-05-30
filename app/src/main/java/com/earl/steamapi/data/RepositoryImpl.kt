package com.earl.steamapi.data

import com.earl.steamapi.data.remoteDataSource.NetworkService
import com.earl.steamapi.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): Repository {
}