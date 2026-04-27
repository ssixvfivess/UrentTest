package com.vyatkina.urenttest.presentation.cities.mapper

import com.vyatkina.urenttest.domain.model.City
import com.vyatkina.urenttest.presentation.cities.model.CityListItemUi
import javax.inject.Inject

class CityListItemUiMapper @Inject constructor() {

    fun map(city: City): CityListItemUi = CityListItemUi(
        id = city.id,
        title = "${city.name}, ${city.country}",
        cityName = city.name,
        countryName = city.country,
        population = city.population,
    )

    fun mapList(cities: List<City>): List<CityListItemUi> = cities.map(::map)
}
