package com.handy.media.player;

import androidx.annotation.NonNull;

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
public class NativeAudioPlayer implements AudioPlayer {

    static {
        System.loadLibrary("media-audio");
    }

    @Override
    public void start() {

    }

    @Override
    public void playAudioBuffer(@NonNull byte[] buffer, int offset, int length) {

    }

    @Override
    public void stop() {

    }
}
