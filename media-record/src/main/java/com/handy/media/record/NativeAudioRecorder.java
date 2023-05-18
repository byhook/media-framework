package com.handy.media.record;

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
public class NativeAudioRecorder implements AudioRecorder {

    static {
        System.loadLibrary("media-audio");
    }

    @Override
    public native void init();

}
