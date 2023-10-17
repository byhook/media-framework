package com.handy.logger;

import android.util.Log;

/**
 * @author: handy
 * @date: 2023-04-04
 * @description:
 */
public class Logger {

    static {
        System.loadLibrary("logger");
    }

    private static final String TAG = "logger";

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;

    public static native void nativeInit(int logLevel);

    /**
     * 先简单实现
     * @param tag
     * @param message
     */
    public static void v(String tag, String message) {
        Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

}
