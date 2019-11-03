package com.google.resumemaker.di

import android.content.Context
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.google.resumemaker.providers.PreferencesImpl
import com.google.resumemaker.providers.PreferencesProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (@NonNull val context: Context) {

    @Provides
    @Singleton
    fun accessContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun accessPreferences(): PreferencesProvider {
        return PreferencesImpl(context)
    }

    @Provides
    @Singleton
    fun accessGson(): Gson {
        val gson by lazy(LazyThreadSafetyMode.NONE) { Gson() }
        return gson
    }
}