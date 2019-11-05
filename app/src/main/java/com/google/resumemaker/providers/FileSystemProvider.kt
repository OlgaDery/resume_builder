package com.google.resumemaker.providers

import android.content.Context
import java.io.File

interface FileSystemProvider {

    suspend fun writeStringToFile (context: Context, data: ByteArray, fileName: String): File
}