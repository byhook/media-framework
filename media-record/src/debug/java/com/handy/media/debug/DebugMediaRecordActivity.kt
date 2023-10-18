package com.handy.media.debug

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.AudioSampleRate
import com.handy.media.common.FileUtils
import com.handy.media.common.IOUtils
import com.handy.media.player.SimpleAudioPlayer
import com.handy.media.record.AudioRecordListener
import com.handy.media.record.SimpleAudioRecorder
import com.handy.media.record.databinding.DebugActivityMediaRecordBinding
import com.handy.module.permission.OnPermissionCallback
import com.handy.module.permission.PermissionUtils
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.concurrent.atomic.AtomicBoolean


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

    /**
     * 录制状态
     */
    private var recordState: AtomicBoolean = AtomicBoolean(false)

    /**
     * 播放状态
     */
    private var playState: AtomicBoolean = AtomicBoolean(false)

    private var debugRecordBinding: DebugActivityMediaRecordBinding? = null

    /**
     * 录制器
     */
    private val audioRecorder by lazy {
        SimpleAudioRecorder()
    }

    /**
     * 播放器
     */
    private val audioPlayer: SimpleAudioPlayer? = null

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
        debugRecordBinding?.btnRecordOperate?.setOnClickListener {
            it as TextView
            if (recordState.compareAndSet(recordState.get(), !recordState.get())) {
                if (recordState.get()) {
                    audioRecorder.init(AudioSampleRate.AUDIO_SAMPLE_RATE_48000, AudioChannels.AUDIO_CHANNEL_STEREO)
                    audioRecorder.setAudioRecordListener(onAudioRecordListener)
                    audioRecorder.start()
                    //刷新UI
                    it.text = "停止录制"
                    debugRecordBinding?.btnPlayOperate?.isEnabled = false
                } else {
                    //停止录制
                    audioRecorder.stop()
                    //刷新UI
                    it.text = "开始录制"
                    debugRecordBinding?.btnPlayOperate?.isEnabled = true
                }
            }
        }
        debugRecordBinding?.btnPlayOperate?.setOnClickListener {
            it as TextView
            if (playState.compareAndSet(playState.get(), !playState.get())) {
                if (playState.get()) {
                    //刷新UI
                    it.text = "停止播放"
                    debugRecordBinding?.btnRecordOperate?.isEnabled = false
                } else {
                    //刷新UI
                    it.text = "开始播放"
                    debugRecordBinding?.btnRecordOperate?.isEnabled = true
                }
            }
        }
    }

    private val onAudioRecordListener = object : AudioRecordListener {

        @Volatile
        private var audioPcmFileStream: FileOutputStream? = null

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

        override fun onRecordStart() {
            val audioDir = FileUtils.getExternalFileDir("localAudio")
            val targetAudioFile = File(audioDir, "${dateFormat.format(System.currentTimeMillis())}.pcm")
                Logger.i(TAG, "onRecordStart ${targetAudioFile.absolutePath}")
            audioPcmFileStream = FileOutputStream(targetAudioFile)
        }

        override fun onRecordBuffer(buffer: ByteArray) {
            audioPcmFileStream?.write(buffer)
            audioPcmFileStream?.flush()
        }

        override fun onRecordStop(errCode: Int) {
            Logger.i(TAG, "onRecordStop errCode:$errCode")
            IOUtils.closeQuietly(audioPcmFileStream)
            audioPcmFileStream = null
        }

    }

}