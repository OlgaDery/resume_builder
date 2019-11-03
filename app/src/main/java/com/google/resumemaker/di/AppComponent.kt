package com.google.resumemaker.di

import com.google.resumemaker.MainViewModel
import com.google.resumemaker.providers.PreferencesImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewModel: MainViewModel)

    fun inject(preferences: PreferencesImpl)
}