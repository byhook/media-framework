package com.handy.media.player

import android.media.AudioFormat
import android.media.AudioTrack
import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.AudioSampleRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

/**
 * @author: handy
 * @date: 2023-10-17
 * @description:
 */
class SimpleFileAudioPlayer(
    private val pcmFile: File, @AudioSampleRate sampleRate: Int, @AudioChannels audioChannels: Int
) : AudioPlayer {

    companion object {
        private const val TAG = "SimpleFileAudioPlayer"
    }

    private val audioPlayer by lazy {
        SimpleAudioPlayer(sampleRate, audioChannels)
    }

    /**
     * 每次读取的数据大小
     */
    @Volatile
    private var minBufferSize: Int = -1

    /**
     * 播放任务
     */
    private var audioPlayJob: Job? = null

    private val workScope by lazy {
        CoroutineScope(Job() + Dispatchers.Default)
    }

    init {
        val targetAudioChannel = if (audioChannels == AudioChannels.AUDIO_CHANNEL_MONO) {
            AudioFormat.CHANNEL_IN_MONO
        } else {
            AudioFormat.CHANNEL_IN_STEREO
        }
        // 获得构建对象的最小缓冲区大小
        minBufferSize = AudioTrack.getMinBufferSize(sampleRate, targetAudioChannel, AudioFormat.ENCODING_PCM_16BIT)
    }

    override fun start() {
        audioPlayJob = workScope.launch {
            while (isActive && minBufferSize > 0) {
                val inputStream = FileInputStream(pcmFile)
                val readBuffer = ByteArray(minBufferSize)
                var readLength: Int = inputStream.read(readBuffer, 0, minBufferSize)
                if (readLength == -1) {
                    Logger.e(TAG, "start break")
                    break
                } else {
                    audioPlayer.playAudioBuffer(readBuffer, 0, readLength)
                }
            }
        }
    }

    override fun playAudioBuffer(buffer: ByteArray, offset: Int, length: Int) {
        //
    }

    override fun stop() {
        workScope.launch {
            audioPlayJob?.cancelAndJoin()
            audioPlayJob = null
        }
    }

}