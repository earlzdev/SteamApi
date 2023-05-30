package com.earl.steamapi.data.remoteDataSource

import android.util.Log
import com.earl.steamapi.domain.models.ErrorResponse
import com.earl.steamapi.domain.SteamApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<SteamApiResponse<T>> = flow {
    emit(SteamApiResponse.Loading)
    withTimeoutOrNull(20000L) {
        val response = call()
        Log.d("tag", "apiRequestFlow: $response")
        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(SteamApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val parsedError: ErrorResponse = Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                    emit(SteamApiResponse.Failure(parsedError.message, parsedError.code))
                }
            }
        } catch (e: Exception) {
            emit(SteamApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(SteamApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)