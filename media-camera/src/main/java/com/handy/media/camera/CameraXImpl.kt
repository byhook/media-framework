package com.handy.media.camera

import android.app.Activity
import android.graphics.SurfaceTexture
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.handy.module.utils.LogUtils

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
class CameraXImpl(private val cameraPreviewView: PreviewView) : CameraSource {

    companion object {
        const val TAG = "CameraXImpl"
    }

    override fun openCamera(cameraId: Int) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(cameraPreviewView.context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraPreviewView.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    cameraPreviewView.context as LifecycleOwner, cameraSelector, preview
                )
            } catch (exc: Exception) {
                LogUtils.d(TAG, "Use case binding failed $exc")
            }
        }, ContextCompat.getMainExecutor(cameraPreviewView.context))
    }

    override fun closeCamera() {

    }

    override fun setPreviewSize(width: Int, height: Int) {

    }

    override fun bindPreviewTexture(surfaceTexture: SurfaceTexture) {

    }

    override fun enablePreview(enable: Boolean) {

    }

    override fun enablePreviewFrame(enable: Boolean) {

    }

    override fun setPreviewFrameCallback(onPreviewFrameCallback: OnPreviewFrameCallback?) {

    }

    override fun release() {

    }

}