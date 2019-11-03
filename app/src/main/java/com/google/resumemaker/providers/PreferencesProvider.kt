package com.google.resumemaker.providers

import java.lang.reflect.Type

interface PreferencesProvider {

    fun <T> getValue(key: String, type: Type): T?
    fun <T> setValue(key: String, type: Type, value: T?): Boolean
    fun deleteValue(key: String): Boolean
}