//
//

#ifndef MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H
#define MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H

#include <aaudio/AAudio.h>
#include "media_audio_recorder.h"

#define TAG "recorder-aaudio"

class AudioRecorderAAudio: public AudioRecorder {


 private:

  AAudioStream *pAudioStream = nullptr;

  void StartStream(AAudioStream *stream);
  void StopStream(AAudioStream *stream);
  void CloseStream(AAudioStream *stream);

  void SetupCommonParameters(AAudioStreamBuilder *builder);
  void SetupRecordingParameters(AAudioStreamBuilder *builder);

 public:

  AudioRecorderAAudio(size_t sampleRate, size_t channels);
  ~AudioRecorderAAudio();

  virtual void StartRecord();

  virtual void StopRecord();

};


#endif //MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H
