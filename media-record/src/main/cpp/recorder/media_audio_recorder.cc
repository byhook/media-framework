//
//

#include "media_audio_recorder.h"
#include "android_debug.h"


AudioRecorder::AudioRecorder(size_t sampleRate, size_t channels) {
  this->sampleRate = sampleRate;
  this->channels = channels;
  this->frameSize = sampleRate * periodTime / 1000;
  this->bufferSize = frameSize * channels * 2;
  LOGI("AudioRecorder constructor "
       "sampleRate:%d, channels:%d, frameSize:%d, bufferSize:%d",
       sampleRate,
       channels,
       frameSize,
       bufferSize
  );
}

AudioRecorder::~AudioRecorder() {
  LOGE("~AudioRecorder destructor");
}