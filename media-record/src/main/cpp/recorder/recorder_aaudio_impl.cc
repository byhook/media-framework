// AAudio 是在 Android O 版本中引入的全新 Android C API

#include "recorder_aaudio_impl.h"
#include "android_debug.h"

AudioRecorderAAudio::AudioRecorderAAudio(size_t sampleRate, size_t channels)
    : AudioRecorder(sampleRate, channels) {

}

AudioRecorderAAudio::~AudioRecorderAAudio() {

}

AAudioStreamBuilder *createStreamBuilder() {
  AAudioStreamBuilder *builder = nullptr;
  aaudio_result_t result = AAudio_createStreamBuilder(&builder);
  if (result != AAUDIO_OK) {
    LOGE("Error creating stream builder: %s",
         AAudio_convertResultToText(result));
  }
  return builder;
}

void AudioRecorderAAudio::StartRecord() {
  AAudioStreamBuilder *builder = createStreamBuilder();
}

void AudioRecorderAAudio::StopRecord() {

}