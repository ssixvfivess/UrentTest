package com.vyatkina.urenttest.domain.usecase

import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.domain.repository.CityRepository
import javax.inject.Inject

interface GetCityUseCase {

    suspend operator fun invoke(
        query: String? = null,
        page: Int = 1,
        limit: Int = 20,
    ): List<City>
}

class GetCityUseCaseImpl @Inject constructor(
    private val repository: CityRepository
): GetCityUseCase {

    override suspend fun invoke(
        query: String?,
        page: Int,
        limit: Int,
    ): List<City> {
        return repository.getCities(
            query = query,
            page = page,
            limit = limit,
        )
    }
}
