//
//

#ifndef MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
#define MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H


class AudioRecorder {

 public:

  AudioRecorder();

  virtual ~AudioRecorder();

  virtual void StartRecord() = 0;

  virtual void StopRecord() = 0;

};


#endif //MEDIA_FRAMEWORK_MEDIA_AUDIO_RECORDER_H
