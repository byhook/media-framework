//
//

#ifndef MEDIA_FRAMEWORK_AUDIO_ENCODER_JNI_H
#define MEDIA_FRAMEWORK_AUDIO_ENCODER_JNI_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

void nativeInit(JNIEnv *, jobject, jint, jint, jint);

void nativeEncodeBuffer(JNIEnv *, jobject);

void nativeRelease(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif

#endif //MEDIA_FRAMEWORK_AUDIO_ENCODER_JNI_H
