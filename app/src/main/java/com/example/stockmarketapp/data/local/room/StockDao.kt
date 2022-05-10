package com.example.stockmarketapp.data.local.room

import androidx.room.*

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingsEntities: List<CompanyListingsEntity>)

    @Query("DELETE FROM CompanyListingsEntity")
    suspend fun deleteCompanyListings()

    @Query("""
        SELECT *
        FROM CompanyListingsEntity
        WHERE LOWER(name)  LIKE '%' + LOWER(:query) + '%' 
               OR UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListings(query: String): List<CompanyListingsEntity>
}