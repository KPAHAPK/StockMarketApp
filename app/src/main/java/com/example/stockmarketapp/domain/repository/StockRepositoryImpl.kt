package com.example.stockmarketapp.domain.repository

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.room.StockDatabase
import com.example.stockmarketapp.data.local.room.toCompanyListings
import com.example.stockmarketapp.data.local.room.toCompanyListingsEntity
import com.example.stockmarketapp.data.remote.StockApi
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>
): StockRepository {

    private val stockDao = db.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow{
            emit(Resource.Loading(true))
            val localListings = stockDao.searchCompanyListings(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListings() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data ${e.message}"))
                null
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data ${e.message}"))
                null
            }

            remoteListings?.let { listings ->
                stockDao.deleteCompanyListings()
                stockDao.insertCompanyListings(
                    listings.map { it.toCompanyListingsEntity() }
                )
                emit(Resource.Success(
                    data = stockDao
                        .searchCompanyListings("")
                        .map {
                            it.toCompanyListings()
                        }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}
