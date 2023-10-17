package com.handy.media.record

/**
 * @author: handy
 * @date: 2023-10-17
 * @description:
 */
interface AudioRecordListener {
    fun onRecordStart()

    fun onRecordBuffer(buffer: ByteArray)

    fun onRecordStop(errCode: Int)

}