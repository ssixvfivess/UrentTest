package com.vyatkina.urenttest.data.mapper

import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.data.model.CityDataApi
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun toDomain(apiModel: CityDataApi): City {
        return City(
            id = apiModel.id,
            name = apiModel.name,
            country = apiModel.country,
            latitude = apiModel.latitude,
            longitude = apiModel.longitude,
            population = apiModel.population
        )
    }

    fun mapList(items: List<CityDataApi>): List<City> = items.map(::toDomain)
}
