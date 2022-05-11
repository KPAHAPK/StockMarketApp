package com.example.stockmarketapp.data.remote

import com.example.stockmarketapp.BuildConfig
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY,
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min&datatype=csv")
    suspend fun getIntraday(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY,
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY,
    ): CompanyInfoDto

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://alphavantage.co"
    }
}