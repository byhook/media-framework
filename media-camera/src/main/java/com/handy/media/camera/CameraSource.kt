package com.handy.media.camera

import android.graphics.SurfaceTexture
import com.handy.media.camera.OnPreviewFrameCallback

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
interface CameraSource {

    fun bindPreviewView()

    fun setupCamera()

    fun setPreviewSize(width: Int, height: Int)

    fun bindPreviewTexture(surfaceTexture: SurfaceTexture)

    fun startPreview()

    fun stopPreview()

    fun enablePreviewFrame(enable: Boolean)

    fun setPreviewFrameCallback(onPreviewFrameCallback: OnPreviewFrameCallback?)

    fun release()

}