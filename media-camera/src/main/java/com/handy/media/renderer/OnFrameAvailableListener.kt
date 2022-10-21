package com.handy.media.renderer

import android.graphics.SurfaceTexture

/**
 * date: 2020-12-15
 * description:
 */
interface OnFrameAvailableListener {

    fun onFrameAvailable(surfaceTexture: SurfaceTexture)

}