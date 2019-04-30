package com.example.movielab.viewModels

import androidx.lifecycle.ViewModel
import com.example.movielab.api.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val movies = movieRepository.movies
    val detailedMovie = movieRepository.detailedMovie
    val isLoading = movieRepository.loadingStatus

    fun fetchMovies() = movieRepository.fetchMovies()

    fun fetchDetailedMovie(movieId: Int) = movieRepository.fetchDetailMovie(movieId)

    override fun onCleared() {
        movieRepository.dispose()
        super.onCleared()
    }
}