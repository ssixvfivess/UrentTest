package com.vyatkina.urenttest.data.repository

import com.vyatkina.urenttest.data.mapper.CityMapper
import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.data.service.CityService
import com.vyatkina.urenttest.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val service: CityService,
    private val mapper: CityMapper,
) : CityRepository {

    override suspend fun getCities(
        query: String?,
        page: Int,
        limit: Int,
    ): List<City> {
        val response = service.getCities(
            query = query,
            page = page,
            limit = limit,
        )
        return mapper.mapList(response.cities)
    }
}
