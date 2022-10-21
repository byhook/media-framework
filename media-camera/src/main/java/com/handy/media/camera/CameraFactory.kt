package com.handy.media.camera

import com.handy.media.camera.CameraSource
import android.os.Build
import com.handy.media.camera.CameraV1Impl
import java.lang.IllegalStateException

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
class CameraFactory private constructor() {

    companion object {
        private const val TAG = "CameraFactory"
        fun makeCamera(): CameraSource {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CameraV1Impl()
            } else {
                CameraV1Impl()
            }
        }

    }

    init {
        throw IllegalStateException("")
    }
}