package com.example.stockmarketapp.data.remote

import com.example.stockmarketapp.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?functions=LISTING_STATUS")
    suspend fun getListings(@Query("apiKey") apiKey:String = API_KEY): ResponseBody

    companion object{
        private const val API_KEY = BuildConfig.API_KEY
        private const val BASE_URL = "https://aplphavantage.co"
    }
}