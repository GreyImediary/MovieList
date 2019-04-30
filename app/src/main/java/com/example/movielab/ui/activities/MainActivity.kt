package com.example.movielab.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielab.R
import com.example.movielab.hide
import com.example.movielab.onEndScroll
import com.example.movielab.show
import com.example.movielab.ui.adapters.MovieApater
import com.example.movielab.viewModels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = MovieApater()

        rv.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
            it.onEndScroll { movieViewModel.fetchMovies() }
        }

        movieViewModel.fetchMovies()

        movieViewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.hide()
            }
        })

        movieViewModel.movies.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

    }
}
