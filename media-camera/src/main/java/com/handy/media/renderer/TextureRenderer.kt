package com.handy.media.renderer

import android.graphics.SurfaceTexture

/**
 * @author: handy
 * date: 2020-11-24
 * description:
 */
interface TextureRenderer {

    fun onSurfaceCreated()

    fun onSurfaceChanged(width: Int, height: Int)

    fun onDrawFrame(surfaceTexture: SurfaceTexture)

}