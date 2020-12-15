package com.handy.media.renderer;

import android.graphics.SurfaceTexture;

/**
 * @author: handy
 * date: 2020-11-24
 * description:
 */
public interface ITextureRenderer {

    void onSurfaceCreated();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame(SurfaceTexture surfaceTexture);

}
