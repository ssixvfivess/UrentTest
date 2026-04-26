package com.vyatkina.urenttest.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitiesDataApi (
    @SerialName("items")
    val cities: List<CityDataApi>,
    @SerialName("limit")
    val limit: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("total")
    val total: Long
) {
}