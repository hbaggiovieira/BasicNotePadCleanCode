package com.example.basicnotepad.core.application

import android.app.Application
import com.example.basicnotepad.home.di.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        val appModules = mutableListOf(notesModule)

//        startKoin {
//            androidLogger(Level.ERROR)
//            androidContext(this@CoreApplication)
//            modules(appModules)
//        }
    }
}