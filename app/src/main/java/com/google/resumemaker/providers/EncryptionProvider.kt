package com.google.resumemaker.providers

interface EncryptionProvider {

    fun encrypt(data: String): String?
    fun decrypt(data: String): String?
}