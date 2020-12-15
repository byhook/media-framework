package com.handy.media.camera;

import android.graphics.SurfaceTexture;

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
public interface ICamera {

    void bindPreviewView();

    void setupCamera();

    void setPreviewSize(int width, int height);

    void bindPreviewTexture(SurfaceTexture surfaceTexture);

    void startPreview();

    void stopPreview();

    void enablePreviewFrame(boolean enable);

    void setPreviewFrameCallback(OnPreviewFrameCallback onPreviewFrameCallback);

    void release();

}
