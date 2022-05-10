package com.example.stockmarketapp.data.local.room

import com.example.stockmarketapp.domain.model.CompanyListings

fun CompanyListingsEntity.toCompanyListings(): CompanyListings {
    return CompanyListings(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListings.toCompanyListingsEntity(): CompanyListingsEntity {
    return CompanyListingsEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}