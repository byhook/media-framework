package com.handy.media.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.handy.module.permission.OnPermissionCallback;
import com.handy.module.permission.PermissionUtils;
import com.handy.module.utils.LogUtils;
import java.util.Arrays;

/**
 * @date: 2020-12-14
 * @description:
 */
public class CameraV1SourceActivity extends AppCompatActivity {

    private static final String TAG = "CameraSourceActivity";

    private GLSurfaceView surfaceView;

    public static void intentStart(Context context) {
        Intent intent = new Intent(context, CameraV1SourceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyPermission();
    }

    private void applyPermission(){
        PermissionUtils.applyPermission(this, new String[]{Manifest.permission.CAMERA}, 200, new OnPermissionCallback() {
            @Override
            public void onRequestPermissionSuccess(String[] permissions, int requestCode) {
                LogUtils.d(TAG,"onRequestPermissionSuccess " + Arrays.toString(permissions));
                setupCamera();
            }

            @Override
            public void onRequestPermissionFailed(String[] permissions, int requestCode) {
                LogUtils.d(TAG,"onRequestPermissionFailed " + Arrays.toString(permissions));
            }
        });
    }

    private void setupCamera(){
        surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(3);
        setContentView(surfaceView);
    }

}
