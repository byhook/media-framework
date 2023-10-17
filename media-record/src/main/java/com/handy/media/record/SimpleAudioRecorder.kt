package com.handy.media.record

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.HandlerThread
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
class SimpleAudioRecorder : AudioRecorder {

    private var audioRecorder: AudioRecord? = null

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

    @RequiresPermission(value = "android.permission.RECORD_AUDIO")
    override fun init(@AudioSampleRate sampleRate: Int, @AudioChannels channels: Int) {
        //如果已经存在了-立即释放
        audioRecorder?.stop()
        audioRecorder?.release()
        //基于配置实例化新的录制器
        val targetAudioChannel = if (channels == AudioChannels.AUDIO_CHANNEL_MONO) {
            AudioFormat.CHANNEL_OUT_MONO
        } else {
            AudioFormat.CHANNEL_OUT_STEREO
        }
        val bufferSizeInBytes =
            AudioRecord.getMinBufferSize(sampleRate, targetAudioChannel, defaultAudioFormat)
        audioRecorder = AudioRecord(
            MediaRecorder.getAudioSourceMax(),
            sampleRate,
            targetAudioChannel,
            defaultAudioFormat,
            bufferSizeInBytes
        )
    }

    override fun setAudioRecordListener(audioRecordListener: AudioRecordListener?) {
        this.audioRecordListener = audioRecordListener
    }

    override fun start() {
        recordJob = workScope.launch {
            while (isActive) {

            }
        }
    }

    override fun stop() {
        recordJob?.cancel()
        recordJob = null
    }

    override fun release() {

    }

}