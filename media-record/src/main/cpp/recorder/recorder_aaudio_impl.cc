// AAudio 是在 Android O 版本中引入的全新 Android C API

#include <cassert>
#include "recorder_aaudio_impl.h"
#include "android_debug.h"

AudioRecorderAAudio::AudioRecorderAAudio(size_t sampleRate, size_t channels)
    : AudioRecorder(sampleRate, channels) {

}

AudioRecorderAAudio::~AudioRecorderAAudio() {

}

AAudioStreamBuilder *createStreamBuilder() {
  AAudioStreamBuilder *builder = nullptr;
  aaudio_result_t result = AAudio_createStreamBuilder(&builder);
  if (result != AAUDIO_OK) {
    LOGE("Error creating stream builder: %s",
         AAudio_convertResultToText(result));
  }
  return builder;
}

aaudio_data_callback_result_t
onRecordDataCallback(AAudioStream *stream, void *userData,
                     void *audioData, int32_t numFrames) {
  if (userData == nullptr) {
    LOGI(TAG, "onRecordDataCallback userData == nullptr");
    return AAUDIO_CALLBACK_RESULT_CONTINUE;
  }
  if (audioData == nullptr) {
    LOGI(TAG, "onRecordDataCallback audioData == nullptr");
    return AAUDIO_CALLBACK_RESULT_CONTINUE;
  }
  AudioRecorderAAudio *pAAudioRecorder =
      static_cast<AudioRecorderAAudio *>(userData);

  if (nullptr != pAAudioRecorder
      && nullptr != pAAudioRecorder->pAudioObserver) {
    pAAudioRecorder->pAudioObserver->OnRecordBuffer(
        static_cast<uint8_t *>(audioData),
        numFrames
    );
    return AAUDIO_CALLBACK_RESULT_CONTINUE;
  }
  return AAUDIO_CALLBACK_RESULT_STOP;
}

/**
 * 如果流出现错误，将调用此函数。 一个常见的错误示例
 * 是指音频设备（例如耳机）断开连接时。
 * @param stream
 * @param data
 * @param error
 */
void onErrorCallback(AAudioStream *stream,
                     void *data,
                     aaudio_result_t error) {
  LOGE(TAG, "onErrorCallback %s", AAudio_convertResultToText(error));
}

void
AudioRecorderAAudio::SetupRecordingParameters(AAudioStreamBuilder *builder) {
  AAudioStreamBuilder_setDeviceId(builder, AAUDIO_UNSPECIFIED);
  AAudioStreamBuilder_setDirection(builder, AAUDIO_DIRECTION_INPUT);
  AAudioStreamBuilder_setSampleRate(builder, sampleRate);
  AAudioStreamBuilder_setChannelCount(builder, channels);
  AAudioStreamBuilder_setDataCallback(builder,
                                      ::onRecordDataCallback,
                                      this);
  //设置公共参数
  SetupCommonParameters(builder);
}

void AudioRecorderAAudio::SetupCommonParameters(AAudioStreamBuilder *builder) {
  AAudioStreamBuilder_setFormat(builder, AAUDIO_FORMAT_PCM_I16);
  // EXCLUSIVE 模式，因为这将提供尽可能低的延迟。
  // 如果独占模式不可用，构建器将退回到共享模式。
  AAudioStreamBuilder_setSharingMode(builder, AAUDIO_SHARING_MODE_EXCLUSIVE);
  AAudioStreamBuilder_setPerformanceMode(builder,
                                         AAUDIO_PERFORMANCE_MODE_LOW_LATENCY
  );
  AAudioStreamBuilder_setErrorCallback(builder, ::onErrorCallback, this);
}

void AudioRecorderAAudio::StartRecord() {
  AAudioStreamBuilder *builder = createStreamBuilder();
  if (nullptr != builder) {
    //初始化录制参数
    SetupRecordingParameters(builder);
    //参数初始化完毕-开始流处理
    aaudio_result_t result = AAudioStreamBuilder_openStream(
        builder,
        &pAudioStream
    );
    if (result == AAUDIO_OK && pAudioStream != nullptr) {
      StartStream(pAudioStream);
      LOGD("create recording stream");
    } else {
      LOGE("Failed to create recording stream. Error: %s",
           AAudio_convertResultToText(result));
    }
    AAudioStreamBuilder_delete(builder);
  } else {
    LOGE("Unable to obtain an AAudioStreamBuilder object");
  }
}

void AudioRecorderAAudio::StopRecord() {
  if (nullptr != pAudioStream) {
    StopStream(pAudioStream);
    CloseStream(pAudioStream);
    pAudioStream = nullptr;
  }
  LOGI(TAG, "StopRecord");
}

void AudioRecorderAAudio::StartStream(AAudioStream *stream) {
  if (nullptr != stream) {
    aaudio_result_t result = AAudioStream_requestStart(stream);
    if (result != AAUDIO_OK) {
      LOGE("Error starting stream. %s", AAudio_convertResultToText(result));
    }
  }
}

void AudioRecorderAAudio::StopStream(AAudioStream *stream) {
  if (nullptr != stream) {
    aaudio_result_t result = AAudioStream_requestStop(pAudioStream);
    if (result != AAUDIO_OK) {
      LOGE("Error stopping stream. %s",
           AAudio_convertResultToText(result));
    }
  }
}

void AudioRecorderAAudio::CloseStream(AAudioStream *stream) {
  if (nullptr != stream) {
    aaudio_result_t result = AAudioStream_close(stream);
    if (result != AAUDIO_OK) {
      LOGE("Error closing stream. %s",
           AAudio_convertResultToText(result));
    }
  }
}