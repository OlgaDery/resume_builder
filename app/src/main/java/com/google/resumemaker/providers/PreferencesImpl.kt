package com.google.resumemaker.providers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.resumemaker.BuildConfig
import java.lang.reflect.Type
import javax.inject.Inject

class PreferencesImpl @Inject constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCE_NAME = "SecurePreferencesStore"
    }

    private var encryptionProvider: EncryptionProvider? = null
    private val preferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    init {
        if (!BuildConfig.DEBUG) {
            // do something for a debug build
            this.encryptionProvider = EncryptionImpl(context)
        }

        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun <T> getValue(key: String, type: Type): T? {
        val encryptedString: String = preferences.getString(key, null) ?: return null
        val decrypted: String?
        decrypted = if (encryptionProvider != null) {
            encryptionProvider?.decrypt(encryptedString)
        } else {
            encryptedString
        }
        return gson.fromJson<T>(decrypted, type)
    }

    override fun <T> setValue(key: String, type: Type, value: T?) {
        val json = gson.toJson(value, type)
        val editor = preferences.edit()
        val encrypted: String?
        if (encryptionProvider != null) {
            encrypted = encryptionProvider?.encrypt(json)
        } else {
            encrypted = json
        }
        editor.putString(key, encrypted)
        editor.apply()
    }

    override fun deleteValue(key: String): Boolean {
        val editor = preferences.edit()
        editor.remove(key)
        return editor.commit()
    }
}