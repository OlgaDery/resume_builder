package com.google.resumemaker.providers

interface EncryptionProvider {

    fun encrypt(data: String): Pair<String?, String?>
    fun decrypt(data: String, iv: String): String?
}