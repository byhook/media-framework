//
//
#include <jni.h>
#include <android/log.h>
#include <string>
#include "media_audio_recorder_jni.h"
#include "android_debug.h"
#include "media_audio_recorder.h"
#include "recorder_opensles_impl.h"


//音频录制器
AudioRecorder *pAudioRecorder = nullptr;

jmethodID onAudioCaptureBuffer = nullptr;

/**
 * 动态注册
 */
JNINativeMethod methods[] = {
    {"nativeInit", "(II)V", (void *) nativeInit},
    {"nativeRecordStart", "()V", (void *) nativeRecordStart},
    {"nativeRecordStop", "()V", (void *) nativeRecordStop},
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

void nativeInit(JNIEnv *env, jobject obj, jint sampleRate, jint channels) {
  LOGD("nativeInit sampleRate:%d channels:%d", sampleRate, channels);
  pAudioRecorder = new AudioRecorderOpenSLES();
  //查找对应的回调方法
  jclass targetClazz = env->FindClass(
      "com/handy/media/record/NativeAudioRecorder"
  );
  onAudioCaptureBuffer = env->GetMethodID(targetClazz,
                                          "onAudioCaptureBuffer",
                                          "(Ljava/nio/ByteBuffer;IJII)V");
  env->DeleteLocalRef(targetClazz);
}

void nativeRecordStart(JNIEnv *env, jobject obj) {
  LOGD("nativeRecordStart %d", onAudioCaptureBuffer);
  if (nullptr != onAudioCaptureBuffer) {
    jobject byteBuffer = nullptr;
    int length = 1024;
    uint8_t buffer[1024] = {0};
    byteBuffer = env->NewDirectByteBuffer(buffer, length);
    env->CallVoidMethod(obj, onAudioCaptureBuffer, byteBuffer, length,
                        (jlong) 0, 100, 50);
    env->DeleteLocalRef(byteBuffer);
  }
}

void nativeRecordStop(JNIEnv *, jobject) {
  LOGD("nativeRecordStop");
}

void nativeRelease(JNIEnv *, jobject) {
  LOGD("nativeRelease");
  if (nullptr != pAudioRecorder) {
    delete pAudioRecorder;
    pAudioRecorder = nullptr;
  }
  onAudioCaptureBuffer = nullptr;
}