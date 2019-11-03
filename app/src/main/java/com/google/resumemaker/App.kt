package com.google.resumemaker

import android.app.Application
import android.content.Context

class App: Application() {

    companion object {
        private var instance: App? = null

        fun getApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}