package com.handy.media.common

import android.app.Application
import android.os.Environment
import java.io.File

/**
 * @author: handy
 * @date: 2023-10-18
 * @description:
 */
object FileUtils {

    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
    }

    private fun isSDCardEnableByEnvironment(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    fun getExternalCacheDir(): File? {
        return null
    }

    fun getExternalFileDir(subDir: String): File? {
        if (isSDCardEnableByEnvironment()) {
            return application?.getExternalFilesDir(subDir)?.apply {
                val exist = !this.exists()
                if (this.isDirectory && exist) {
                    this.mkdirs()
                }
            }
        }
        return null
    }

}