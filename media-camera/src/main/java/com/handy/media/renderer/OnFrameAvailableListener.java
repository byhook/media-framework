package com.handy.media.renderer;

import android.graphics.SurfaceTexture;

/**
 * date: 2020-12-15
 * description:
 */
public interface OnFrameAvailableListener {

    void onFrameAvailable(SurfaceTexture surfaceTexture);

}
