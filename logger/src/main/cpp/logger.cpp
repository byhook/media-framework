//
//
#include <jni.h>
#include <string>
#include "logger.h"

extern "C" JNIEXPORT void JNICALL
Java_com_handy_logger_Logger_nativeInit(JNIEnv *env,
                                        jclass clazz,
                                        jint _log_level) {
  LOGD("TAG", "nativeInit => %d", _log_level);
}
