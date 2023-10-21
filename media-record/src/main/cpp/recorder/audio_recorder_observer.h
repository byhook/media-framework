//
//

#ifndef MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H
#define MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H


class OnAudioRecorderObserver {

 public:

  virtual void OnRecordStart() = 0;

  virtual void OnRecordBuffer() = 0;

  virtual void OnRecordStop() = 0;

};


#endif //MEDIA_FRAMEWORK_AUDIO_RECORDER_OBSERVER_H
