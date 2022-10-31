package com.handy.media.camera

import android.graphics.SurfaceTexture
import com.handy.media.constants.CameraType

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
interface CameraSource {

    fun openCamera(@CameraType cameraId: Int)

    fun closeCamera()

    fun setPreviewSize(width: Int, height: Int)

    fun bindPreviewTexture(surfaceTexture: SurfaceTexture)

    fun enablePreview(enable: Boolean)

    fun enablePreviewFrame(enable: Boolean)

    fun setPreviewFrameCallback(onPreviewFrameCallback: OnPreviewFrameCallback?)

    fun release()

}