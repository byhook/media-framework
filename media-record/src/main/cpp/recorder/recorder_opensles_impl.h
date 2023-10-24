//
//

#ifndef MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H
#define MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "media_audio_recorder.h"

#define TAG "recorder-opensles"

enum RecordState {
  STATE_READY = 0,
  STATE_RECORDING = 1,
  STATE_STOP = 2
};

class AudioRecorderOpenSLES: public AudioRecorder {

 private:

  SLEngineItf engineEngine = nullptr;
  SLObjectItf engineObject = nullptr;
  SLAndroidConfigurationItf configItf = NULL;
  //录制对象
  SLObjectItf recorderObject = NULL;
  SLAndroidSimpleBufferQueueItf recorderBuffQueueItf = NULL; //Buffer接口

 public:
  //录制状态
  volatile RecordState recordState = STATE_READY;
  uint8_t *recordBuffer;
  size_t recordBufferSize;
  //录制接口
  SLRecordItf recorderRecord = NULL;

  AudioRecorderOpenSLES(size_t sampleRate, size_t channels);
  ~AudioRecorderOpenSLES();

  virtual void StartRecord();

  virtual void StopRecord();


};


#endif //MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H
