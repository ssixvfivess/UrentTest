package com.vyatkina.urenttest.data.service

import com.vyatkina.urenttest.data.model.CitiesDataApi

interface CityService {

    suspend fun getCities(
        query: String?,
        page: Int,
        limit: Int,
    ): CityServiceResult<CitiesDataApi>
}
