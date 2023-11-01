//
//

#include "audio_encoder_jni.h"


/**
 * 动态注册
 */
JNINativeMethod methods[] = {
    {"nativeInit", "(III)V", (void *) nativeInit},
    {"nativeEncodeBuffer", "()V", (void *) nativeEncodeBuffer},
    {"nativeRelease", "()V", (void *) nativeRelease}
};

/**
 * 动态注册
 * @param env
 * @return
 */
jint registerNativeMethod(JNIEnv *env) {
  jclass cl =
      env->FindClass("com/handy/media/encode/NativeAudioEncoder");
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

void nativeInit(JNIEnv *env,
                jobject obj,
                jint sampleRate,
                jint channels,
                jint bitRate) {

}

void nativeEncodeBuffer(JNIEnv *, jobject) {

}

void nativeRelease(JNIEnv *, jobject) {

}