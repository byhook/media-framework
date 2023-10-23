//
//

#ifndef MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H
#define MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H


#include <cstdint>
class OnAudioRecorderObserver {

 public:

  virtual void OnRecordStart() = 0;

  virtual void OnRecordBuffer(uint8_t * buffer, size_t bufferSize) = 0;

  virtual void OnRecordStop() = 0;

};


#endif //MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H
