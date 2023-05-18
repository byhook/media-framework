//
//
#include <jni.h>
#include <android/log.h>
#include "media_audio_recorder.h"

#define LOG_TAG "NativeAudioRecorder"

#define LOGV(format, args...)  __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, format, ##args);
#define LOGD(format, args...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, format, ##args);
#define LOGI(format, args...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, format, ##args);
#define LOGW(format, args...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG, format, ##args);
#define LOGE(format, args...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, format, ##args);

/**
 * 动态注册
 */
JNINativeMethod methods[] = {
    {"init", "()V", (void *) nativeInit},
};

/**
 * 动态注册
 * @param env
 * @return
 */
jint registerNativeMethod(JNIEnv *env) {
  jclass cl =
      env->FindClass("com/handy/media/record/NativeAudioRecorder");
  if ((env->RegisterNatives(cl, methods, sizeof(methods) / sizeof(methods[0])))
      < 0) {
    return -1;
  }
  return 0;
}

/**
 * 加载默认回调
 * @param vm
 * @param reserved
 * @return
 */
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
  JNIEnv *env = NULL;
  if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
    return -1;
  }
  //注册方法
  if (registerNativeMethod(env) != JNI_OK) {
    return -1;
  }
  return JNI_VERSION_1_6;
}

void nativeInit(JNIEnv *, jobject) {
  LOGD("nativeInit");
}