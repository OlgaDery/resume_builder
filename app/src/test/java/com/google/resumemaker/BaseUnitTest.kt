package com.google.resumemaker

import android.content.res.Resources
import org.mockito.Mock
import com.google.resumemaker.providers.*
import org.mockito.Mockito

open class BaseUnitTest {

    @Mock
    lateinit var mockResources: Resources


    val preferences: PreferencesProvider
    get() {
        return Mockito.mock(PreferencesProvider::class.java)
    }

}