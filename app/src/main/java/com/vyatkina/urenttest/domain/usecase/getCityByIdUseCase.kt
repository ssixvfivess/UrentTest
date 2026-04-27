package com.vyatkina.urenttest.domain.usecase

import com.vyatkina.urenttest.base.CoroutineDispatchers
import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.domain.repository.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

interface GetCityByIdUseCase {

    suspend operator fun invoke(id: Int): City?
}

class GetCityByIdUseCaseImpl @Inject constructor(
    private val repository: CityRepository,
    private val coroutineDispatchers: CoroutineDispatchers,
) : GetCityByIdUseCase {

    override suspend fun invoke(id: Int): City? {
        return withContext(coroutineDispatchers.io()) {
            repository.getCityById(id)
        }
    }
}
