//
//
#include <jni.h>
#include <android/log.h>
#include <sys/prctl.h>
#include <sys/syscall.h>
#include <string>
#include "media_audio_recorder_jni.h"
#include "android_debug.h"
#include "media_audio_recorder.h"
#include "recorder_opensles_impl.h"

JavaVM *g_JVM = nullptr;
jobject g_Obj = nullptr;

//音频录制器
AudioRecorder *pAudioRecorder = nullptr;

jmethodID onAudioCaptureBuffer = nullptr;

/**
 * 底层的录制回调
 */
class OnRecorderObserver: public OnAudioRecorderObserver {

 protected:
  JNIEnv *localEnv = nullptr;

  void OnRecordStart() override {
    JNIEnv *env = nullptr;
    if (nullptr != g_JVM &&
        g_JVM->AttachCurrentThread(&env, nullptr) == JNI_OK) {
      LOGD("OnRecordStart...");
    } else {
      LOGE("OnRecordStart... AttachCurrentThread error");
    }
  }

  void OnRecordBuffer(uint8_t *buffer, size_t length) {
    if (nullptr != localEnv && nullptr != g_Obj &&
        nullptr != onAudioCaptureBuffer) {
      LOGD("OnRecordBuffer...");
      if (nullptr != localEnv) {
        /*
        int length = 1024;
        uint8_t buffer[1024] = {0};
        */
        jobject byteBuffer = localEnv->NewDirectByteBuffer(
            buffer, length);
        localEnv->CallVoidMethod(g_Obj, onAudioCaptureBuffer,
                                 byteBuffer,
                                 (jint) length,
                                 (jlong) 0, 100, 50);
        localEnv->DeleteLocalRef(byteBuffer);
      }
    }
  }

  virtual void OnRecordStop() {
    LOGE("OnRecordStop...");
    if (nullptr != g_JVM) {
      g_JVM->DetachCurrentThread();
    }
    localEnv = nullptr;
  }

};

OnRecorderObserver *pOnRecorderObserver = nullptr;

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
  //保存全局JavaVM
  g_JVM = vm;
  return JNI_VERSION_1_6;
}

void nativeInit(JNIEnv *env, jobject obj, jint sampleRate, jint channels) {
  LOGD("nativeInit sampleRate:%d channels:%d", sampleRate, channels);
  //查找对应的回调方法
  jclass targetClazz = env->FindClass(
      "com/handy/media/record/NativeAudioRecorder"
  );
  onAudioCaptureBuffer = env->GetMethodID(targetClazz,
                                          "onAudioCaptureBuffer",
                                          "(Ljava/nio/ByteBuffer;IJII)V");
  env->DeleteLocalRef(targetClazz);
  //回调
  pOnRecorderObserver = new OnRecorderObserver();
  pAudioRecorder = new AudioRecorderOpenSLES(sampleRate, channels);
  pAudioRecorder->SetOnAudioRecorderObserver(pOnRecorderObserver);
}

void nativeRecordStart(JNIEnv *env, jobject obj) {
  //保存全局引用
  g_Obj = env->NewGlobalRef(obj);

  char szThreadName[20] = {0};
  prctl(PR_GET_NAME, szThreadName);
  LOGD("nativeRecordStart %s", szThreadName);

  pAudioRecorder->StartRecord();
}

void nativeRecordStop(JNIEnv *env, jobject obj) {
  LOGD("nativeRecordStop");
  pAudioRecorder->StopRecord();
  pOnRecorderObserver = nullptr;
  if (nullptr != g_Obj) {
    env->DeleteGlobalRef(g_Obj);
    g_Obj = nullptr;
  }
}

void nativeRelease(JNIEnv *, jobject) {
  LOGD("nativeRelease");
  if (nullptr != pAudioRecorder) {
    delete pAudioRecorder;
    pAudioRecorder = nullptr;
  }
  if (nullptr != pOnRecorderObserver) {
    delete pOnRecorderObserver;
    pOnRecorderObserver = nullptr;
  }
  onAudioCaptureBuffer = nullptr;
}