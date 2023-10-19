//
//
#include <jni.h>
#include <android/log.h>
#include "media_audio_recorder.h"
#include "android_debug.h"

/**
 * 动态注册
 */
JNINativeMethod methods[] = {
    {"nativeInit", "(II)V", (void *) nativeInit},
    {"nativeRelease", "()V", (void *) nativeRelease}
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

void nativeInit(JNIEnv *, jobject, jint sampleRate, jint channels) {
  LOGD("nativeInit sampleRate:%d channels:%d", sampleRate, channels);
}


void nativeRelease(JNIEnv *, jobject) {
  LOGD("nativeRelease");
}