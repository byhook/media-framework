package com.handy.media.record;

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
public class NativeOpenAudioRecorder implements AudioRecorder {

    static {
        System.loadLibrary("media-audio");
    }

    @Override
    public native void init();

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }

}
