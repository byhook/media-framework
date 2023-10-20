//
//

#ifndef MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
#define MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H


#include <cstdint>
class AudioRecorder {


 protected:

  //采样率
  size_t sampleRate = 44100;
  //频道数
  size_t channels = 2;
  //音频帧周期20ms
  size_t periodTime = 20;
  //每帧音频大小
  size_t frameSize = 0;
  //数据大小
  size_t bufferSize = 0;

 public:

  AudioRecorder(size_t sampleRate, size_t channels);

  virtual ~AudioRecorder();

  virtual void StartRecord() = 0;

  virtual void StopRecord() = 0;

};


#endif //MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
