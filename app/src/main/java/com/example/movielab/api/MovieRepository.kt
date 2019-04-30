package com.example.movielab.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movielab.AnyMap
import com.example.movielab.models.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MovieRepository(private val movieApi: MovieApi) {
    val movies = MutableLiveData<MutableList<Movie>>()
    val detailedMovie = MutableLiveData<Movie>()
    val loadingStatus = MutableLiveData<Boolean>()
    var page = 0.0
    private val disposable = CompositeDisposable()

    fun fetchMovies() {
        ++page
        loadingStatus.postValue(true)
        val subscription = movieApi.fetchMovies(page)
                .subscribeOn(Schedulers.io())
                .map {
                    page = it["page"] as Double

                    val movieJsonList = it["results"] as MutableList<AnyMap>

                    movieJsonList.map { movie -> movieFromJson(movie) }
                }
                .doOnComplete { loadingStatus.postValue(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (it.isNotEmpty()) {
                                postMovies(it.toMutableList())
                            }
                        },
                        onError = { Log.e("MR-fetchMovies", it.message) }
                )

        disposable.add(subscription)
    }

    fun fetchDetailMovie(movieId: Int) {
        loadingStatus.postValue(true)
        val subscription = movieApi.fetchDetailedMovie(movieId)
                .subscribeOn(Schedulers.io())
                .map {
                    movieFromJson(it)
                }
                .doOnComplete { loadingStatus.postValue(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { detailedMovie.postValue(it) },
                        onError = { Log.e("MR-fetchDetailedMovie", it.message) }
                )

        disposable.add(subscription)
    }

    private fun movieFromJson(movieJson: AnyMap): Movie {
        val id = movieJson["id"] as Double
        val originalTitle = movieJson["original_title"] as String
        val title = movieJson["title"] as String
        val posterPath = movieJson["backdrop_path"] as String?
        val overview = movieJson["overview"] as String
        val releaseDate = movieJson["release_date"] as String
        val voteAverage = movieJson["vote_average"] as Double
        var genres: String? = null
        var tagline: String? = null
        var budget: Double? = null
        var runtime: Double? = null
        var revenue = 0.0

        if (movieJson["genres"] != null) {
            genres = genresFromJson(movieJson["genres"] as List<AnyMap>)
        }

        if (movieJson["tagline"] != null) {
            tagline = movieJson["tagline"] as String
        }

        if (movieJson["budget"] != null) {
            budget = movieJson["budget"] as Double
        }

        if (movieJson["runtime"] != null) {
            runtime = movieJson["runtime"] as Double
        }

        if (movieJson["revenue"] != null) {
            revenue = movieJson["revenue"] as Double
        }

        return Movie(
                id,
                originalTitle,
                title,
                posterPath,
                overview,
                releaseDate,
                voteAverage,
                genres,
                tagline,
                budget,
                runtime,
                revenue)
    }

    fun dispose() {
        disposable.dispose()
    }

    private fun genresFromJson(genres: List<AnyMap>) = genres.joinToString(separator = ", ") { it["name"] as String }

    private fun postMovies(movieList: MutableList<Movie>) {
        if (movies.value == null) {
            movies.postValue(movieList)
        } else {
            val list = movies.value?.apply {
                addAll(movieList)
            }

            movies.postValue(list)
        }
    }
}