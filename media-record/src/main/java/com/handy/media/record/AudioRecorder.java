package com.handy.media.record;

/**
 * @author: handy
 * @date: 2023-05-18
 * @description:
 */
public interface AudioRecorder {

    /**
     * 初始化
     */
    void init(@AudioSampleRate int sampleRate, @AudioChannels int channels);

    /**
     * 开始录制
     */
    void start();

    /**
     * 停止录制
     */
    void stop();

    void setAudioRecordListener(AudioRecordListener audioRecordListener);

    /**
     * 释放
     */
    void release();

}
