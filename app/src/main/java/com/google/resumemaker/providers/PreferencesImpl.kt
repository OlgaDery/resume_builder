package com.google.resumemaker.providers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class PreferencesImpl @Inject constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCE_NAME = "SecurePreferencesStore"
        private const val IV_KEY_SUFFIX = "_IV"
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
       // val iv: String = preferences.getString("${key}_IV", null) ?: return defaultValue
      //  val json = encryptionProvider.decrypt(encryptedString, iv) ?: return defaultValue
        System.out.println("string found: " + encryptedString)
        return gson.fromJson<T>(encryptedString, type)
    }

    override fun <T> setValue(key: String, type: Type, value: T?): Boolean {
        val json = gson.toJson(value, type)
//        val (encryptedValue, iv) = encryptionProvider.encrypt(json)
//        if (encryptedValue != null && iv != null) {
            val editor = preferences.edit()
            System.out.println("saving: " + json)
            editor.putString(key, json)//
         //   editor.putString("$key$IV_KEY_SUFFIX", iv)
            return editor.commit() //.apply()
       // }
      //  return false
    }

    override fun deleteValue(key: String): Boolean {
        val editor = preferences.edit()
        editor.remove(key)
        editor.remove("$key$IV_KEY_SUFFIX")
        return editor.commit()
    }
}