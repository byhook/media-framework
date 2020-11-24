package com.handy.media.camera;

import android.os.Build;

/**
 * @author: handy
 * @date: 2020-11-24
 * @description:
 */
public class CameraFactory {

    private static final String TAG = "CameraFactory";

    private CameraFactory(){
        throw new IllegalStateException("");
    }

    public static ICamera makeCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            return new CameraImpl();
        } else {
            return new Camera2Impl();
        }
    }

}
