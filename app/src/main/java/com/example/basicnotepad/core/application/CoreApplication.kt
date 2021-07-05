package com.example.basicnotepad.core.application

import android.app.Application
import com.example.basicnotepad.di.notesModule

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