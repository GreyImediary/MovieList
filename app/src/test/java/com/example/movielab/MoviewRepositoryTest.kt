package com.example.movielab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movielab.api.MovieRepository
import com.example.movielab.di.baseApiModule
import com.example.movielab.di.movieApiModule
import com.google.common.truth.Truth.assertThat
import com.proact.poject.serku.proact.RxSchedulerRule
import com.proact.poject.serku.proact.testObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.junit.MockitoJUnit

class MoviewRepositoryTest : KoinTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()!!

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val movieRepository: MovieRepository by inject()

    @Before
    fun setUp() {
        startKoin(listOf(baseApiModule, movieApiModule))
    }


    @Test
    fun fetchMoviesTest() {
        val liveData = movieRepository.movies.testObserver()

        movieRepository.fetchMovies()

        assertThat(liveData.observedValues.first())
            .isNotEmpty()

        assertThat(liveData.observedValues.first()?.first())
            .isNotNull()

    }

    @Test
    fun fetchDtailedMoviesTest() {
        val liveData = movieRepository.detailedMovie.testObserver()

        movieRepository.fetchDetailMovie(299534)

        assertThat(liveData.observedValues.first())
                .isNotNull()

        println(liveData.observedValues.first())
    }

    @After
    fun setDown() {
        stopKoin()
    }
}