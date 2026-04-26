package com.vyatkina.urenttest.domain.repository

import com.vyatkina.urenttest.domain.model.City

interface CityRepository {

    suspend fun getCities(
        query: String? = null,
        page: Int = 1,
        limit: Int = 20,
    ): List<City>
}
