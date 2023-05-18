package com.handy.media.debug

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.handy.media.record.NativeAudioRecorder
import com.handy.module.permission.OnPermissionCallback
import com.handy.module.permission.PermissionUtils
import com.handy.module.utils.LogUtils
import java.util.Arrays

/**
 * @author: handy
 * @date: 2023-03-20
 * @description:
 */
class DebugMediaRecordActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "DebugMediaRecordActivity"

        fun intentStart(context: Context) {
            val intent = Intent(context, DebugMediaRecordActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mediaRecorder by lazy {
        NativeAudioRecorder()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPermission()
    }

    private fun setupPermission() {
        PermissionUtils.applyPermission(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200,
            object : OnPermissionCallback {
                override fun onRequestPermissionSuccess(permissions: Array<out String>?, requestCode: Int) {
                    LogUtils.d(TAG, "onRequestPermissionSuccess " + Arrays.toString(permissions))
                    mediaRecorder.init()
                }

                override fun onRequestPermissionFailed(permissions: Array<out String>?, requestCode: Int) {
                    LogUtils.d(TAG, "onRequestPermissionFailed " + Arrays.toString(permissions))
                }

            })
    }

}