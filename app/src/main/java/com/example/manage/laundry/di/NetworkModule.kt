package com.example.manage.laundry.di

import com.example.manage.laundry.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://192.168.100.125:1705/api"

    @Provides
    @Singleton
    fun provideHttpClient(
//        @Named("authToken") token: String?
    ): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
//        install(Auth) {
//            bearer {
//                loadTokens {
//                    token?.let { BearerTokens(it, it) }
//                }
//            }
//        }
    }

    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): ApiService = ApiService(client, BASE_URL)
}