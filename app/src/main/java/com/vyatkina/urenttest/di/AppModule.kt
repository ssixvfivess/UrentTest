package com.vyatkina.urenttest.di

import com.vyatkina.urenttest.base.CoroutineDispatchers
import com.vyatkina.urenttest.base.CoroutineDispatchersImpl
import com.vyatkina.urenttest.base.ResourceProvider
import com.vyatkina.urenttest.base.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Reusable
    fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider

    @Binds
    @Reusable
    fun bindCoroutineDispatchers(impl: CoroutineDispatchersImpl): CoroutineDispatchers
}