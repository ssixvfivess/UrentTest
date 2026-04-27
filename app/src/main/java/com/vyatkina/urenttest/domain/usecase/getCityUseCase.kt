package com.vyatkina.urenttest.domain.usecase

import com.vyatkina.urenttest.base.CoroutineDispatchers
import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.domain.repository.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

interface GetCityUseCase {

    suspend operator fun invoke(
        query: String? = null,
        page: Int = 1,
        limit: Int = 20,
    ): List<City>
}

class GetCityUseCaseImpl @Inject constructor(
    private val repository: CityRepository,
    private val coroutineDispatchers: CoroutineDispatchers,
): GetCityUseCase {

    override suspend fun invoke(
        query: String?,
        page: Int,
        limit: Int,
    ): List<City> {
        return withContext(coroutineDispatchers.io()) {
            repository.getCities(
                query = query,
                page = page,
                limit = limit,
            )
        }
    }
}
