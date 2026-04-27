package com.vyatkina.urenttest.data.repository

import com.vyatkina.urenttest.base.CoroutineDispatchers
import com.vyatkina.urenttest.data.mapper.CityMapper
import com.vyatkina.urenttest.data.service.CityService
import com.vyatkina.urenttest.data.service.CityServiceResult
import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.domain.repository.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

class CityRepositoryImpl @Inject constructor(
    private val service: CityService,
    private val mapper: CityMapper,
    private val coroutineDispatchers: CoroutineDispatchers,
) : CityRepository {

    private val cachedCities = linkedMapOf<Int, City>()
    private val detailsPageSize = 100

    override suspend fun getCities(
        query: String?,
        page: Int,
        limit: Int,
    ): List<City> {
        return withContext(coroutineDispatchers.io()) {
            when (
                val response = service.getCities(
                    query = query,
                    page = page,
                    limit = limit,
                )
            ) {
                is CityServiceResult.Error -> throw response.exception
                is CityServiceResult.Success -> cacheCities(mapper.mapList(response.data.cities))
            }
        }
    }

    override suspend fun getCityById(id: Int): City? {
        return withContext(coroutineDispatchers.io()) {
            cachedCities[id]?.let { return@withContext it }

            var currentPage = 1

            while (currentPage > 0) {
                val response = when (
                    val serviceResult = service.getCities(
                        query = null,
                        page = currentPage,
                        limit = detailsPageSize,
                    )
                ) {
                    is CityServiceResult.Error -> throw serviceResult.exception
                    is CityServiceResult.Success -> serviceResult.data
                }
                val cities = cacheCities(mapper.mapList(response.cities))
                cities.firstOrNull { it.id == id }?.let { return@withContext it }

                val loadedItemsCount = response.page * response.limit
                if (loadedItemsCount >= response.total || cities.isEmpty()) {
                    return@withContext null
                }

                currentPage += 1
            }

            null
        }
    }

    private fun cacheCities(cities: List<City>): List<City> {
        cities.forEach { city ->
            cachedCities[city.id] = city
        }
        return cities
    }
}
