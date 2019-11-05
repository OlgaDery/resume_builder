package com.google.resumemaker

import android.content.Context
import android.content.res.Resources
import com.google.resumemaker.providers.PreferencesImpl
import com.google.resumemaker.providers.PreferencesProvider
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.util.prefs.Preferences

open class BaseUnitTest {

    @Mock
    lateinit var mockResources: Resources

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var preferences: PreferencesProvider
}