package com.handy.media.common

import java.io.Closeable

/**
 * @author: handy
 * @date: 2023-10-18
 * @description:
 */
object IOUtils {

    fun closeQuietly(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }

}