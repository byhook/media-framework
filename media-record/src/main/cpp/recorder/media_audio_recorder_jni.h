//
//

#ifndef MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H
#define MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H

#ifdef __cplusplus
extern "C" {
#endif

void nativeInit(JNIEnv *, jobject, jint, jint);

void nativeRecordStart(JNIEnv *, jobject);

void nativeRecordStop(JNIEnv *, jobject);

void nativeRelease(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif

#endif //MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H
