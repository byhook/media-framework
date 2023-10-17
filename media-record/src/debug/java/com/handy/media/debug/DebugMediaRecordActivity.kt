package com.handy.media.debug

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.handy.media.record.NativeOpenAudioRecorder
import com.handy.media.record.R
import com.handy.media.record.SimpleAudioRecorder
import com.handy.media.record.databinding.DebugActivityMediaRecordBinding
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

    private var debugRecordBinding: DebugActivityMediaRecordBinding? = null

    private val mediaRecorder by lazy {
        SimpleAudioRecorder()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugRecordBinding = DebugActivityMediaRecordBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        setupPermission()
    }

    private fun setupPermission() {
        PermissionUtils.applyPermission(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200,
            object : OnPermissionCallback {
                override fun onRequestPermissionSuccess(permissions: Array<out String>?, requestCode: Int) {
                    LogUtils.d(TAG, "onRequestPermissionSuccess " + Arrays.toString(permissions))
                    setupViews()
                }

                override fun onRequestPermissionFailed(permissions: Array<out String>?, requestCode: Int) {
                    LogUtils.d(TAG, "onRequestPermissionFailed " + Arrays.toString(permissions))
                }

            })
    }

    private fun setupViews() {
        debugRecordBinding?.btnRecordStart?.setOnClickListener {
            mediaRecorder.start()
        }
        debugRecordBinding?.btnRecordStop?.setOnClickListener {
            mediaRecorder.stop()
        }
    }

}