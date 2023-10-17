package com.handy.media.debug

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.record.AudioRecordListener
import com.handy.media.AudioSampleRate
import com.handy.media.record.SimpleAudioRecorder
import com.handy.media.record.databinding.DebugActivityMediaRecordBinding
import com.handy.module.permission.OnPermissionCallback
import com.handy.module.permission.PermissionUtils
import java.util.Arrays

/**
 * @author: handy
 * @date: 2023-03-20
 * @description:
 */
class DebugMediaRecordActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "DebugMediaRecordPage"

        private const val REQUEST_CODE = 200

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
        PermissionUtils.applyPermission(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE,
            object : OnPermissionCallback {
                override fun onRequestPermissionSuccess(permissions: Array<out String>?, requestCode: Int) {
                    Logger.d(TAG, "onRequestPermissionSuccess " + Arrays.toString(permissions))
                    setupViews()
                }

                override fun onRequestPermissionFailed(permissions: Array<out String>?, requestCode: Int) {
                    Logger.d(TAG, "onRequestPermissionFailed " + Arrays.toString(permissions))
                }

            })
    }

    @SuppressLint("MissingPermission")
    private fun setupViews() {
        debugRecordBinding?.btnRecordStart?.setOnClickListener {
            mediaRecorder.init(AudioSampleRate.AUDIO_SAMPLE_RATE_48000, AudioChannels.AUDIO_CHANNEL_STEREO)
            mediaRecorder.setAudioRecordListener(onAudioRecordListener)
            mediaRecorder.start()
        }
        debugRecordBinding?.btnRecordStop?.setOnClickListener {
            mediaRecorder.stop()
        }
    }

    private val onAudioRecordListener = object : AudioRecordListener {

        override fun onRecordStart() {
            Logger.i(TAG, "onRecordStart")
        }

        override fun onRecordBuffer(buffer: ByteArray) {

        }

        override fun onRecordStop(errCode: Int) {
            Logger.i(TAG, "onRecordStop errCode:$errCode")
        }

    }

}