package com.example.movielab

import android.app.Application
import com.example.movielab.di.baseApiModule
import com.example.movielab.di.movieApiModule
import com.example.movielab.di.vmModule
import org.koin.android.ext.android.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(baseApiModule, movieApiModule, vmModule))
    }
}