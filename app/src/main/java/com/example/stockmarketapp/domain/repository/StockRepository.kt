package com.example.stockmarketapp.domain.repository

import com.example.stockmarketapp.domain.model.CompanyListings
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListings>>>
}