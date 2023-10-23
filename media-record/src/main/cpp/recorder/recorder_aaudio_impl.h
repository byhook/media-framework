//
//

#ifndef MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H
#define MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H

#include <aaudio/AAudio.h>
#include "media_audio_recorder.h"

class AudioRecorderAAudio: public AudioRecorder {

 public:

  AudioRecorderAAudio(size_t sampleRate, size_t channels);
  ~AudioRecorderAAudio();

  virtual void StartRecord();

  virtual void StopRecord();

};


#endif //MEDIA_FRAMEWORK_RECORDER_AAUDIO_IMPL_H
