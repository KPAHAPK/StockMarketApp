package com.example.stockmarketapp.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompanyListingsEntity")
data class CompanyListingsEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey
    val id: Int? = null
)