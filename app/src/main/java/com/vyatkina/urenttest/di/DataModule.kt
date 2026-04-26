package com.vyatkina.urenttest.di
import com.vyatkina.urenttest.data.repository.CityRepositoryImpl
import com.vyatkina.urenttest.data.service.CityService
import com.vyatkina.urenttest.data.service.CityServiceImpl
import com.vyatkina.urenttest.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Reusable
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    @Reusable
    abstract fun bindCityService(impl: CityServiceImpl): CityService

    @Module
    @InstallIn(SingletonComponent::class)
    object Providers {

        @Provides
        @Singleton
        fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            coerceInputValues = true
            isLenient = true
        }

        @Provides
        @Singleton
        fun provideHttpClient(json: Json): HttpClient {
            return HttpClient(OkHttp) {
                expectSuccess = true

                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTP
                        host = BASE_HOST
                        port = BASE_PORT
                    }
                }

                install(ContentNegotiation) {
                    json(json)
                }

                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.BODY
                }
            }
        }

        private const val BASE_HOST = "dev-dep.tools.urent.tech"
        private const val BASE_PORT = 8080
    }
}
