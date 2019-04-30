package com.example.movielab.models

data class Movie(
        val id: Double,
        val originalTitle: String,
        val title: String,
        val posterPath: String?,
        val overview: String,
        val releaseDate: String,
        val voteAverage: Double,
        val genres: String?,
        val tagline: String?,
        val budget: Double?,
        val runtime: Double?,
        val revenue: Double

)