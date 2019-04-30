package com.example.movielab.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movielab.*
import com.example.movielab.viewModels.MovieViewModel
import kotlinx.android.synthetic.main.activity_detailed_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailedMovieActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_movie)

        val movieId = intent.getDoubleExtra(movieIdKey, -1.0)
        movieViewModel.fetchDetailedMovie(movieId.toInt())

        movieViewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.hide()
            }
        })

        movieViewModel.detailedMovie.observe(this, Observer {
            setPoster(it.posterPath)
            movieTitle.text = it.title
            originalTitle.text = it.originalTitle
            rating.text = it.voteAverage.toString()
            releaseDate.text = it.releaseDate

            if (it.tagline != null) {
                tagline.text = it.tagline
            }

            if (it.genres != null) {
                genres.text = getString(R.string.genres, it.genres)
            }

            if (it.budget != null) {
                budget.text = getString(R.string.budget, it.budget.toInt().toString())
            }

            if (it.revenue != 0.0) {
                revenue.show()
                revenue.text = getString(R.string.revenue, it.revenue.toInt().toString())
            }

            if (it.runtime != null) {
                runtime.text = getString(R.string.runtime, it.runtime.toInt().toString())
            }

            description.text = it.overview
        })
    }

    private fun setPoster(posterPath: String?) {
        if (posterPath != null) {
            val posterFullPath = posterDefaultPath + posterPath

            Glide.with(this)
                    .load(posterFullPath)
                    .centerCrop()
                    .into(moviePoster)

        }
    }
}