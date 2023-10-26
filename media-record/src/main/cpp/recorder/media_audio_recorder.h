//
//

#ifndef MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
#define MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H


#include <cstdint>
#include "audio_recorder_observer.h"

class AudioRecorder {

 protected:

  //音频帧周期20ms
  size_t periodTime = 20;
  //每帧音频大小
  size_t frameSize = 0;
  //数据大小
  size_t bufferSize = 0;

 public:

  //采样率
  size_t sampleRate = 48000;
  //频道数
  size_t channels = 2;

  void (*pOnAudioCaptureBuffer)(uint8_t *buffer, size_t bufferSize) = nullptr;

 public:

  AudioRecorder(size_t sampleRate, size_t channels);

  virtual ~AudioRecorder();

  virtual void SetOnAudioRecorderObserver(void (*onAudioCaptureBuffer)(
      uint8_t *buffer,
      size_t bufferSize
  )) {
    this->pOnAudioCaptureBuffer = onAudioCaptureBuffer;
  }

  virtual void StartRecord() = 0;

  virtual void StopRecord() = 0;

};


#endif //MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
