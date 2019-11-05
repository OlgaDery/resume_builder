package com.google.resumemaker.providers

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadFactory
import com.google.crypto.tink.aead.AeadKeyTemplates
import android.content.Context
import com.google.crypto.tink.Config
import com.google.crypto.tink.config.TinkConfig
import java.io.IOException
import java.security.GeneralSecurityException
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.subtle.Base64
import java.io.UnsupportedEncodingException

class EncryptionImpl(val context: Context) : EncryptionProvider {

    private val aead: Aead
    private val PREF_FILE_NAME = "hello_world_pref"
    private val TINK_KEYSET_NAME = "hello_world_keyset"
    private val MASTER_KEY_URI = "android-keystore://hello_world_master_key"
    private val EMPTY_ASSOCIATED_DATA = ByteArray(0)

    init {
        try {
            Config.register(TinkConfig.LATEST)
            aead = AeadFactory.getPrimitive(getOrGenerateNewKeysetHandle())
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    private fun getOrGenerateNewKeysetHandle(): KeysetHandle {
        return AndroidKeysetManager.Builder()
            .withSharedPref(context, TINK_KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
    }

    override fun encrypt(data: String): String? {
        try {
            val plaintext = data.toByteArray()
            val ciphertext = aead.encrypt(plaintext, EMPTY_ASSOCIATED_DATA)
            return base64Encode(ciphertext)

        } catch (e: UnsupportedEncodingException) {
            println(e.printStackTrace())

        } catch (e: GeneralSecurityException) {
            println(e.printStackTrace())

        } catch (e: IllegalArgumentException) {
            println(e.printStackTrace())

        }
        return null
    }

    override fun decrypt(data: String): String? {
        try {
            val cipherText = base64Decode(data)
            val plaintext = aead.decrypt(cipherText, EMPTY_ASSOCIATED_DATA)

            return String(plaintext)

        } catch (e: UnsupportedEncodingException) {
            println(e.printStackTrace())

        } catch (e: GeneralSecurityException) {
            println(e.printStackTrace())

        } catch (e: IllegalArgumentException) {
            println(e.printStackTrace())
        }

        return null
    }

    private fun base64Encode(input: ByteArray): String {
        return Base64.encodeToString(input, Base64.DEFAULT)
    }

    private fun base64Decode(input: String): ByteArray {
        return Base64.decode(input, Base64.DEFAULT)
    }
}