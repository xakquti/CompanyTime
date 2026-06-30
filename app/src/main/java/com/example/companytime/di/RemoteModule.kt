package com.example.companytime.di

import com.example.companytime.data.ApiService
import com.example.companytime.data.AuthInterceptor
import com.example.companytime.data.PersonPagingDataSource
import com.example.companytime.data.TokenStorage
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val remoteModule = module {

    single{
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }

    single { TokenStorage(get()) }

    single { AuthInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single<ApiService>{
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl("http://192.168.1.8:8080")
            .client(get<OkHttpClient>())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}