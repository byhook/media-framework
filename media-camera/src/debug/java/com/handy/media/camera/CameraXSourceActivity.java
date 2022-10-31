package com.handy.media.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
import com.handy.module.permission.OnPermissionCallback;
import com.handy.module.permission.PermissionUtils;
import com.handy.module.utils.LogUtils;
import java.util.Arrays;

/**
 * @date: 2020-12-14
 * @description:
 */
public class CameraXSourceActivity extends AppCompatActivity {

    private static final String TAG = "CameraSourceActivity";

    private PreviewView cameraPreviewView;

    public static void intentStart(Context context) {
        Intent intent = new Intent(context, CameraXSourceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyPermission();
    }

    private void applyPermission() {
        PermissionUtils.applyPermission(this, new String[]{Manifest.permission.CAMERA}, 200,
                new OnPermissionCallback() {
                    @Override
                    public void onRequestPermissionSuccess(String[] permissions, int requestCode) {
                        LogUtils.d(TAG, "onRequestPermissionSuccess " + Arrays.toString(permissions));
                        setupCamera();
                    }

                    @Override
                    public void onRequestPermissionFailed(String[] permissions, int requestCode) {
                        LogUtils.d(TAG, "onRequestPermissionFailed " + Arrays.toString(permissions));
                    }
                });
    }

    private void setupCamera() {
        cameraPreviewView = new PreviewView(this);
        setContentView(cameraPreviewView);
        CameraSource cameraSource = new CameraXImpl(cameraPreviewView);
        cameraSource.openCamera(0);
    }

}
