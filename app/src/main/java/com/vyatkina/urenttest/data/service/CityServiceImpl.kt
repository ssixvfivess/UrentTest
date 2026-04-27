package com.vyatkina.urenttest.data.service

import com.vyatkina.urenttest.data.model.CitiesDataApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.io.IOException
import javax.inject.Inject
import kotlinx.serialization.SerializationException

class CityServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CityService {

    override suspend fun getCities(
        query: String?,
        page: Int,
        limit: Int,
    ): CityServiceResult<CitiesDataApi> {
        return try {
            val response = httpClient.get(CITIES_PATH) {
                parameter(QUERY_PARAMETER, query?.takeIf { it.isNotBlank() })
                parameter(PAGE_PARAMETER, page)
                parameter(LIMIT_PARAMETER, limit)
            }.body<CitiesDataApi>()

            CityServiceResult.Success(response)
        } catch (exception: RedirectResponseException) {
            CityServiceResult.Error(CityServiceException.Client(exception))
        } catch (exception: ClientRequestException) {
            CityServiceResult.Error(CityServiceException.Client(exception))
        } catch (exception: ServerResponseException) {
            CityServiceResult.Error(CityServiceException.Server(exception))
        } catch (exception: SerializationException) {
            CityServiceResult.Error(CityServiceException.Serialization(exception))
        } catch (exception: IOException) {
            CityServiceResult.Error(CityServiceException.Network(exception))
        } catch (exception: Exception) {
            CityServiceResult.Error(CityServiceException.Unknown(exception))
        }
    }

    private companion object {
        const val CITIES_PATH = "/api/cities"
        const val QUERY_PARAMETER = "query"
        const val PAGE_PARAMETER = "page"
        const val LIMIT_PARAMETER = "limit"
    }
}
