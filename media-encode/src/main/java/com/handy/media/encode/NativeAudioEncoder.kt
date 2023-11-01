package com.handy.media.encode

/**
 * @author: handy
 * @date: 2023-11-01
 * @description:
 */
class NativeAudioEncoder : AudioEncoder {

    init {
        System.loadLibrary("audio-encoder")
    }

    override fun init(sampleRate: Int, channels: Int, bitRate: Int) {

    }

    override fun release() {

    }

    private external fun nativeInit(sampleRate: Int, channels: Int, bitRate: Int)

    private external fun nativeEncodeBuffer()

    private external fun nativeRelease()

}