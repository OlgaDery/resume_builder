package com.google.resumemaker.providers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

open class PreferencesImpl @Inject constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCE_NAME = "SecurePreferencesStore"
    }

    private val encryptionProvider: EncryptionProvider
    private val preferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    init {
        this.encryptionProvider = EncryptionImpl(context)
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun <T> getValue(key: String, type: Type): T? {
        val encryptedString: String = preferences.getString(key, null) ?: return null
        val decrypted = encryptionProvider.decrypt(encryptedString)
        return gson.fromJson<T>(decrypted, type)
    }

    override fun <T> setValue(key: String, type: Type, value: T?) {
        val json = gson.toJson(value, type)
            val editor = preferences.edit()
            val encrypted = encryptionProvider.encrypt(json)
            editor.putString(key, encrypted)
            editor.apply()
    }

    override fun deleteValue(key: String): Boolean {
        val editor = preferences.edit()
        editor.remove(key)
        return editor.commit()
    }
}