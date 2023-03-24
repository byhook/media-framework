package com.handy.media.record;

/**
 * @author: handy
 * @date: 2023-03-22
 * @description:
 */
public class NativeMediaRecorder {

    static {
        System.loadLibrary("media-record");
    }

    public native void nativeInit();

}
