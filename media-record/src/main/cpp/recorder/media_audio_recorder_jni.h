//
//

#ifndef MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H
#define MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H

void nativeInit(JNIEnv *, jobject, jint, jint);

void nativeRecordStart(JNIEnv *, jobject);

void nativeRecordStop(JNIEnv *, jobject);

void nativeRelease(JNIEnv *, jobject);

#endif //MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_JNI_H
