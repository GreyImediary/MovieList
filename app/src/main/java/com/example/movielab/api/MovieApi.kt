package com.example.movielab.api

import com.example.movielab.AnyMap
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular?api_key=e204326fed074d12b61320ab34175e2d&language=ru")
    fun fetchMovies(@Query("page") page: Double): Observable<AnyMap>

    @GET("movie/{movie_id}?api_key=e204326fed074d12b61320ab34175e2d&language=ru")
    fun fetchDetailedMovie(@Path("movie_id") movieId: Int): Observable<AnyMap>
}