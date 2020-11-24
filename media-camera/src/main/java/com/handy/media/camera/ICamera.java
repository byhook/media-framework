package com.handy.media.camera;

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
public interface ICamera {

    void bindPreviewView();

    void setupCamera();

    void setPreviewSize(int width, int height);

    void startPreview();

    void stopPreview();

    void setOnOpenCameraListener();

    void release();

}
