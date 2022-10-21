package com.handy.media.camera

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
interface OnPreviewFrameCallback {

    fun onPreviewFrame(data: ByteArray, camera: CameraSource)

}