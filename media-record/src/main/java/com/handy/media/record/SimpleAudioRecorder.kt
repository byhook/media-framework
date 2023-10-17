package com.handy.media.record

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import com.handy.logger.LimitLogger
import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.AudioSampleRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
class SimpleAudioRecorder : AudioRecorder {

    companion object {
        private const val TAG = "SimpleAudioRecorder"
    }

    @Volatile
    private var audioRecorder: AudioRecord? = null

    /**
     * 默认音频格式
     */
    private var defaultAudioFormat = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 录制任务
     */
    private var recordJob: Job? = null

    private val workScope by lazy {
        CoroutineScope(Job() + Dispatchers.Default)
    }

    /**
     * 录制音频的回调
     */
    private var audioRecordListener: AudioRecordListener? = null

    /**
     * 每次读取的数据大小
     */
    @Volatile
    private var minBufferSize: Int = -1

    /**
     * 录制状态
     */
    private var recordState: AtomicBoolean = AtomicBoolean(false)

    /**
     * 频控日志
     */
    private val limitLogger: LimitLogger by lazy {
        LimitLogger(TAG, 3000)
    }

    @RequiresPermission(value = "android.permission.RECORD_AUDIO")
    override fun init(@AudioSampleRate sampleRate: Int, @AudioChannels channels: Int) {
        //如果已经存在了-立即释放
        audioRecorder?.stop()
        audioRecorder?.release()
        //基于配置实例化新的录制器
        val targetAudioChannel = if (channels == AudioChannels.AUDIO_CHANNEL_MONO) {
            AudioFormat.CHANNEL_IN_MONO
        } else {
            AudioFormat.CHANNEL_IN_STEREO
        }
        val bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRate, targetAudioChannel, defaultAudioFormat)
        minBufferSize = bufferSizeInBytes
        audioRecorder = AudioRecord(
            MediaRecorder.getAudioSourceMax(), sampleRate, targetAudioChannel, defaultAudioFormat, bufferSizeInBytes
        )
        Logger.i(TAG, "init bufferSize:$bufferSizeInBytes sampleRate:$sampleRate channels:$channels")
    }

    override fun setAudioRecordListener(audioRecordListener: AudioRecordListener?) {
        this.audioRecordListener = audioRecordListener
    }

    override fun start() {
        if (recordState.compareAndSet(false, true)) {
            try {
                audioRecorder?.startRecording()
                launchRecordStart()
            } catch (e: IllegalStateException) {
                //启动录制异常
                audioRecordListener?.onRecordStop(-1)
            }
            Logger.i(TAG, "start record invoke")
        } else {
            Logger.e(TAG, "start ignore")
        }
    }

    private fun launchRecordStart() {
        recordJob = workScope.launch {
            if (audioRecorder?.state == AudioRecord.STATE_UNINITIALIZED) {
                Logger.e(TAG, "start ignore because state is uninitialized")
                return@launch
            }
            if (minBufferSize <= 0) {
                Logger.e(TAG, "start ignore because bufferSize is error")
                return@launch
            }
            Logger.i(TAG, "record start")
            withContext(Dispatchers.Main) {
                audioRecordListener?.onRecordStart()
            }
            var buffer = ByteArray(minBufferSize)
            while (isActive) {
                var length = audioRecorder?.read(buffer, 0, buffer.size) ?: 0
                if (length > 0) {
                    audioRecordListener?.onRecordBuffer(buffer)
                    limitLogger.d(TAG,"onRecordBuffer bufferSize:${buffer.size}")
                }
            }
            Logger.i(TAG, "record stop")
        }
    }

    override fun stop() {
        if (recordState.compareAndSet(true, false)) {
            workScope.launch {
                recordJob?.cancelAndJoin()
                recordJob = null
                //停止录制
                try {
                    audioRecorder?.stop()
                    withContext(Dispatchers.Main) {
                        audioRecordListener?.onRecordStop(0)
                    }
                } catch (e: IllegalStateException) {
                    //停止录制异常
                    withContext(Dispatchers.Main) {
                        audioRecordListener?.onRecordStop(-2)
                    }
                }
                Logger.i(TAG, "stop record invoke")
            }
        } else {
            Logger.e(TAG, "stop ignore")
        }
    }

    override fun release() {
        stop()
    }

}