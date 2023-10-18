package com.handy.media.player

import com.handy.logger.Logger
import com.handy.media.AudioChannels
import com.handy.media.AudioSampleRate
import com.handy.media.common.IOUtils
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

    private var audioPcmFileStream: FileInputStream? = null

    init {
        // 获得构建对象的最小缓冲区大小
        minBufferSize = audioPlayer.getMinBufferSize()
        Logger.i(TAG,"init minBufferSize:$minBufferSize " +
                "sampleRate:$sampleRate channels:$audioChannels")
    }

    override fun start() {
        audioPlayJob = workScope.launch {
            audioPcmFileStream = FileInputStream(pcmFile)
            audioPlayer.start()
            while (isActive && minBufferSize > 0) {
                val readBuffer = ByteArray(minBufferSize)
                var readLength = audioPcmFileStream?.read(readBuffer, 0, minBufferSize) ?: -1
                if (readLength == -1) {
                    Logger.e(TAG, "start break")
                    break
                } else {
                    audioPlayer.playAudioBuffer(readBuffer, 0, readLength)
                }
            }
            Logger.e(TAG, "start play complete")
            //停止播放
            stop()
        }
    }

    override fun playAudioBuffer(buffer: ByteArray, offset: Int, length: Int) {
        //
    }

    override fun stop() {
        workScope.launch {
            //等待停止播放
            audioPlayJob?.cancelAndJoin()
            audioPlayJob = null
            //停止播放
            audioPlayer.stop()
            //关闭流操作
            IOUtils.closeQuietly(audioPcmFileStream)
            audioPcmFileStream = null
            Logger.i(TAG, "stop play complete")
        }
    }

}