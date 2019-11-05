package com.google.resumemaker.providers

import android.content.Context
import java.io.File
import javax.inject.Inject

class FileSystemImpl @Inject constructor(): FileSystemProvider {

    override suspend fun writeStringToFile(context: Context, data: ByteArray, fileName: String
    ): File {
        val newFile: File
        val file1 = File(context.cacheDir, fileName)
        if (!file1.exists()) {
            file1.mkdirs()
            newFile = File (file1, fileName)
            newFile.createNewFile()
            if (newFile.isFile) {
                newFile.writeBytes(data)
            }
        } else {
            newFile = File (file1, fileName)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
            if (newFile.isFile) {
                newFile.writeBytes(data)
            }
        }
        return newFile
    }
}