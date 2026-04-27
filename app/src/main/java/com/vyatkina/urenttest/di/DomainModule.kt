package com.vyatkina.urenttest.di

import com.vyatkina.urenttest.domain.usecase.GetCityUseCase
import com.vyatkina.urenttest.domain.usecase.GetCityByIdUseCase
import com.vyatkina.urenttest.domain.usecase.GetCityByIdUseCaseImpl
import com.vyatkina.urenttest.domain.usecase.GetCityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    @Reusable
    fun bindGetCityUseCase(impl: GetCityUseCaseImpl): GetCityUseCase

    @Binds
    @Reusable
    fun bindGetCityByIdUseCase(impl: GetCityByIdUseCaseImpl): GetCityByIdUseCase

}
