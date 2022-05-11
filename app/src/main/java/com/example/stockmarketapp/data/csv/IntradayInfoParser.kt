package com.example.stockmarketapp.data.csv

import com.example.stockmarketapp.data.mapper.toIntraday
import com.example.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0)
                    val close = line.getOrNull(4)?.toDouble()
                    IntradayInfoDto(
                        timestamp = timestamp ?: return@mapNotNull null,
                        close = close ?: return@mapNotNull null,
                    ).toIntraday()
                }.filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }.sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}