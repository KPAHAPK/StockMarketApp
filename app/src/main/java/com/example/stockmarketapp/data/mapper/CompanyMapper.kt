package com.example.stockmarketapp.data.mapper

import com.example.stockmarketapp.data.local.room.CompanyListingsEntity
import com.example.stockmarketapp.domain.model.CompanyListing

fun CompanyListingsEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingsEntity {
    return CompanyListingsEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}