package com.handy.media.camera;

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
public interface OnPreviewFrameCallback {

    void onPreviewFrame(byte[] data, ICamera camera);

}
