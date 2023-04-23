package com.handy.media.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.handy.logger.Logger
import com.handy.media.camera.CameraV1SourceActivity
import com.handy.media.camera.CameraXSourceActivity
import com.handy.media.debug.DebugMediaRecordActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.nativeInit(Logger.LEVEL_DEBUG)
    }

    fun onOpenCameraV1Click(view: View?) {
        CameraV1SourceActivity.intentStart(this)
    }

    fun onOpenCameraXClick(view: View?) {
        CameraXSourceActivity.intentStart(this)
    }

    fun onDebugMediaRecordClick(view: View?) {
        DebugMediaRecordActivity.intentStart(this)
    }

}