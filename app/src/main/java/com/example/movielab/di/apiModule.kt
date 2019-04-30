package com.example.movielab.di

import com.example.movielab.api.MovieApi
import com.example.movielab.api.MovieRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "BASE_URL"

fun createBaseUrl() = "https://api.themoviedb.org/3/"

fun createGson() = Gson()

fun createLogging() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

fun createClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

fun createGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

fun createRx2JavaCallbackFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

inline fun <reified T> createApi(
    baseUrl: String,
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
): T = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(gsonConverterFactory)
    .addCallAdapterFactory(rxJava2CallAdapterFactory).build().create(T::class.java)

val baseApiModule = module {
    single(BASE_URL) { createBaseUrl() }
    single { createGson() }
    single { createLogging() }
    single { createClient(get()) }
    single { createGsonConverter() }
    single { createRx2JavaCallbackFactory() }
}

val movieApiModule = module {
    single { createApi<MovieApi>(get(), get(), get(), get()) }
    factory { MovieRepository(get()) }
}