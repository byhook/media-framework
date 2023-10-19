package com.handy.media.record;

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
public class NativeAudioRecorder implements AudioRecorder {

    static {
        System.loadLibrary("media-recorder");
    }

    @Override
    public void init(int sampleRate, int channels) {

    }

    @Override
    public void setAudioRecordListener(AudioRecordListener audioRecordListener) {

    }

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
