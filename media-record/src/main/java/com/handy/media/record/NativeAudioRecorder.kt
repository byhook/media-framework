package com.handy.media.record

import com.handy.logger.Logger
import com.handy.media.annotation.CalledByNative
import java.nio.ByteBuffer

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
class NativeAudioRecorder : AudioRecorder {

    private var onAudioRecordListener: AudioRecordListener? = null

    override fun init(sampleRate: Int, channels: Int) {
        nativeInit(sampleRate, channels)
    }

    override fun setAudioRecordListener(audioRecordListener: AudioRecordListener) {
        this.onAudioRecordListener = audioRecordListener
    }

    override fun start() {
        onAudioRecordListener?.onRecordStart()
        nativeRecordStart()
    }

    override fun stop() {
        nativeRecordStop()
        onAudioRecordListener?.onRecordStop(0)
    }

    override fun release() {
        nativeRelease()
    }

    private external fun nativeInit(sampleRate: Int, channels: Int)

    /**
     * 采集的音频回调
     * 由native调用
     *
     * @param length
     * @param byteBuffer
     * @param timestamp
     * @param sampleRate
     * @param channels
     */
    @CalledByNative
    fun onAudioCaptureBuffer(byteBuffer: ByteBuffer, length: Int, timestamp: Long, sampleRate: Int, channels: Int) {
        val buffer = ByteArray(length)
        byteBuffer[buffer]
        Logger.d(
            TAG, "onAudioCaptureBuffer buffer:$buffer timestamp:$timestamp " +
                    "sampleRate:$sampleRate channels:$channels"
        )
        onAudioRecordListener?.onRecordBuffer(buffer)
    }

    private external fun nativeRecordStart()
    private external fun nativeRecordStop()
    private external fun nativeRelease()

    companion object {

        private const val TAG = "NativeAudioRecorder"

        init {
            System.loadLibrary("media-recorder")
        }
    }
}