package com.handy.media.application

import android.app.Application
import com.handy.media.common.FileUtils

/**
 * @author: handy
 * @date: 2023-10-18
 * @description:
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FileUtils.init(this)
    }

}