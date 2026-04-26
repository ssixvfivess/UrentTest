package com.vyatkina.urenttest.data.service

import com.vyatkina.urenttest.data.model.CitiesDataApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class CityServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CityService {

    override suspend fun getCities(
        query: String?,
        page: Int,
        limit: Int,
    ): CitiesDataApi {
        return httpClient.get(CITIES_PATH) {
            parameter(QUERY_PARAMETER, query?.takeIf { it.isNotBlank() })
            parameter(PAGE_PARAMETER, page)
            parameter(LIMIT_PARAMETER, limit)
        }.body()
    }

    private companion object {
        const val CITIES_PATH = "/api/cities"
        const val QUERY_PARAMETER = "query"
        const val PAGE_PARAMETER = "page"
        const val LIMIT_PARAMETER = "limit"
    }
}
