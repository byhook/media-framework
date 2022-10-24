package com.handy.media.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.handy.media.camera.CameraV1SourceActivity
import com.handy.media.camera.CameraXSourceActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onOpenCameraV1Click(view: View?) {
        CameraV1SourceActivity.intentStart(this)
    }

    fun onOpenCameraXClick(view: View?) {
        CameraXSourceActivity.intentStart(this)
    }

}