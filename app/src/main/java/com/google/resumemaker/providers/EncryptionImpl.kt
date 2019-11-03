package com.google.resumemaker.providers

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.Config
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadFactory
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.SecureRandom

class EncryptionImpl: EncryptionProvider {

    companion object {
        private const val KEYSET_NAME = "TinkEncryptionProvider"
        private const val MASTER_KEY_URI = "android-keystore://tink_encryption_provider_master_key"
    }

    private val aead: Aead
    private val context: Context

    constructor(context: Context) {
        this.context = context
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
            .withSharedPref(context, KEYSET_NAME, "")//PreferencesKeys.TINK_ENCRYPTION_KEY.value
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
    }

    //region EncryptionProvider

    override fun encrypt(data: String): Pair<String?, String?> {
        try {
            val randomSecureRandom = SecureRandom()
            val iv = ByteArray(32)
            randomSecureRandom.nextBytes(iv)
            val encrypted = aead.encrypt(data.toByteArray(), iv)
            return Pair(null, null)//(encrypted.base64Encode(), iv.base64Encode())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(null, null)
    }

    override fun decrypt(data: String, iv: String): String? {
        try {
            val decrypted = ""//aead.decrypt(data.base64Decode(), iv.base64Decode())
            return null//String(decrypted)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    //endregion
}