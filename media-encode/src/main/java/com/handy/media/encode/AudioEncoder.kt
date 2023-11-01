package com.handy.media.encode

/**
 * @author: handy
 * @date: 2023-11-01
 * @description:
 */
interface AudioEncoder {

    /**
     * 初始化
     */
    fun init(sampleRate: Int, channels: Int, bitRate: Int)

    /**
     * 释放
     */
    fun release()

}