package com.example.stockmarketapp.data.local.room

import com.example.stockmarketapp.domain.model.CompanyListing

fun CompanyListingsEntity.toCompanyListings(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingsEntity(): CompanyListingsEntity {
    return CompanyListingsEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}