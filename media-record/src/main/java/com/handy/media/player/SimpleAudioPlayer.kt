package com.handy.media.player

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.handy.logger.LimitLogger
import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.AudioSampleRate
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author: handy
 * @date: 2023-10-17
 * @description:
 */
class SimpleAudioPlayer(
    @AudioSampleRate sampleRate: Int, @AudioChannels audioChannels: Int
) : AudioPlayer {

    companion object {
        private const val TAG = "SimpleAudioPlayer"
    }

    private val defaultAudioFormat = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 实际播放类
     */
    private var audioTrack: AudioTrack? = null

    /**
     * 每次读取的数据大小
     */
    @Volatile
    private var minBufferSize: Int = -1

    /**
     * 播放状态
     */
    private var playState: AtomicBoolean = AtomicBoolean(false)

    /**
     * 频控日志
     */
    private val limitLogger: LimitLogger by lazy {
        LimitLogger(TAG, 3000)
    }

    init {
        val targetAudioChannel = if (audioChannels == AudioChannels.AUDIO_CHANNEL_MONO) {
            AudioFormat.CHANNEL_OUT_MONO
        } else {
            AudioFormat.CHANNEL_OUT_STEREO
        }
        // 获得构建对象的最小缓冲区大小
        minBufferSize = AudioTrack.getMinBufferSize(sampleRate, targetAudioChannel, defaultAudioFormat)
        //组装参数
        val targetAttrs = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        val audioFormat = AudioFormat.Builder().setEncoding(defaultAudioFormat).setSampleRate(sampleRate)
            .setChannelMask(targetAudioChannel).build()
        audioTrack = AudioTrack(
            targetAttrs, audioFormat, minBufferSize, AudioTrack.MODE_STREAM, AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    open fun getMinBufferSize(): Int {
        return minBufferSize
    }

    override fun start() {
        if (playState.compareAndSet(false, true)) {
            try {
                audioTrack?.play()
            } catch (e: IllegalStateException) {
                //
            }
            Logger.i(TAG, "start play invoke")
        } else {
            Logger.e(TAG, "stop play ignore")
        }
    }

    override fun playAudioBuffer(buffer: ByteArray, offset: Int, length: Int) {
        try {
            val writeResult = audioTrack?.write(buffer, offset, length)
            Logger.d(
                TAG, "playAudioBuffer " +
                        "bufferSize:${buffer.size} offset:$offset length:$length writeResult:$writeResult"
            )
        } catch (e: Exception) {
            limitLogger.e("playAudioBufferError", "playAudioBuffer error:${e.message}")
        }
    }

    override fun stop() {
        if (playState.compareAndSet(true, false)) {
            audioTrack?.stop()
            audioTrack?.release()
            audioTrack = null
            Logger.i(TAG, "stop play invoke")
        } else {
            Logger.e(TAG, "stop play ignore")
        }
    }

}