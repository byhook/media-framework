//
//

#ifndef MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H
#define MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "media_audio_recorder.h"

class AudioRecorderOpenSLES: public AudioRecorder {

 private:

  SLEngineItf engineEngine = nullptr;
  SLObjectItf engineObject = nullptr;

 public:

  AudioRecorderOpenSLES();
  ~AudioRecorderOpenSLES();

};


#endif //MEDIA_FRAMEWORK_AUDIO_RECORDER_OPENSL_IMPL_H
