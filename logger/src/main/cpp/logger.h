//
//

#ifndef MEDIA_FRAMEWORK_LOGGER_H
#define MEDIA_FRAMEWORK_LOGGER_H

#include <jni.h>
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

#define LOGD(LOG_TAG, ...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(LOG_TAG, ...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(LOG_TAG, ...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(LOG_TAG, ...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(LOG_TAG, ...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__) // 定义LOGF类型

#ifdef __cplusplus
}
#endif

#endif //MEDIA_FRAMEWORK_LOGGER_H
