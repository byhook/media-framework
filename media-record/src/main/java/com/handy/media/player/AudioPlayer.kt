package com.handy.media.player

/**
 * @author: handy
 * @date: 2023-10-17
 * @description:
 */
interface AudioPlayer {

    /**
     * 开始播放
     */
    fun start()

    /**
     * 播放音频流
     */
    fun playAudioBuffer(buffer: ByteArray, offset: Int, length: Int)

    /**
     * 停止播放
     */
    fun stop()

}