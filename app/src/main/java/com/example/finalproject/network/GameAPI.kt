package com.example.finalproject.network

import com.example.finalproject.model.Game
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://www.freetogame.com/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface GameApiService {
    @GET("games")
    suspend fun getGames(): List<Game>

    //TODO Define second GET function, include game id in query.
}

object GameAPI {
    val retrofitService : GameApiService by lazy {
        retrofit.create(GameApiService::class.java)
    }
}
