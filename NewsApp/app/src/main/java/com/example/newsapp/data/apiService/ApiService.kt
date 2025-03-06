package com.example.newsapp.data.apiService

import com.example.newsapp.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "5fe6757c8418408b99928e27b1b15bfc"
    ): ApiResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q") q : String = "",
        @Query("apiKey") apiKey: String = "5fe6757c8418408b99928e27b1b15bfc"
    ):ApiResponse
}