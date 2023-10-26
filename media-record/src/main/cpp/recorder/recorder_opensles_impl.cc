//
//
#include "assert.h"
#include "android_debug.h"
#include "recorder_opensles_impl.h"

#define TAG "recorder-opensles"

#define NUM_BUFFER_QUEUE 1
#define NUM_RECORDER_EXPLICIT_INTERFACES 2

#define SL_ASSERT(x)                   \
  do {                                \
    assert(SL_RESULT_SUCCESS == (x)); \
    (void)(x);                        \
  } while (0)

AudioRecorderOpenSLES::AudioRecorderOpenSLES(size_t sampleRate,
                                             size_t channels) :
    AudioRecorder(sampleRate, channels) {
  // 创建引擎对象，调用全局方法创建一个引擎对象（OpenSL ES唯一入口）
  SLresult result;
  //1、创建引擎
  result = slCreateEngine(&engineObject,
                          0,
                          nullptr, //配置参数
                          0, //支持的接口数量
                          nullptr, //具体的要支持的接口，是枚举的数组
                          nullptr);
  SL_ASSERT(result);
  //2、实例化引擎
  result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
  SL_ASSERT(result);
  //3、获取引擎接口，这是创建其他对象所需要的
  result = (*engineObject)->GetInterface(engineObject,
                                         SL_IID_ENGINE,
                                         &engineEngine);
  SL_ASSERT(result);
  LOGI(TAG, "AudioRecorderOpenSLES constructor");
}

/**
 * 录制音频时的回调
 * 这是一个单独的采集线程
 * @param bufferQueue
 * @param context
 */
void OnAudioRecorderCallback(SLAndroidSimpleBufferQueueItf bufferQueue,
                             void *context) {
  //注意这个是另外一条采集线程回调
  AudioRecorderOpenSLES *pRecorderContext = (AudioRecorderOpenSLES *) context;
  if (pRecorderContext->recordBuffer != NULL) {
    //回调开始录制
    SLuint32 state;
    SLresult result = (*(pRecorderContext->recorderRecord))->GetRecordState(
        pRecorderContext->recorderRecord, &state
    );
    SL_ASSERT(result);
    if (state != SL_RECORDSTATE_RECORDING) {
      return;
    }
    if (nullptr != pRecorderContext->pOnAudioCaptureBuffer) {
      //回调录制中
      pRecorderContext->pOnAudioCaptureBuffer(
          pRecorderContext->recordBuffer,
          pRecorderContext->recordBufferSize
      );

      //取完数据，需要调用Enqueue触发下一次数据回调
      result = (*bufferQueue)->Enqueue(
          bufferQueue, pRecorderContext->recordBuffer,
          pRecorderContext->recordBufferSize
      );
      SL_ASSERT(result);
    }
  }
}

void AudioRecorderOpenSLES::StartRecord() {
  LOGI(TAG, "StartRecord");
  if (nullptr == engineEngine) {
    LOGE("StartRecord ignore engineEngine is null");
    return;
  }
  SLresult result;
  SLDataLocator_IODevice ioDevice = {
      SL_DATALOCATOR_IODEVICE,  //类型
      SL_IODEVICE_AUDIOINPUT, //音频输入类型
      SL_DEFAULTDEVICEID_AUDIOINPUT, //设备ID
      NULL//device实例
  };
  SLDataSource dataSource = {
      &ioDevice, //配置输入
      NULL//输入格式，采集的并不需要
  };
  // 数据源简单缓冲队列定位器,输出buffer队列
  SLDataLocator_AndroidSimpleBufferQueue simpleBufferQueue = {
      SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE, //类型
      NUM_BUFFER_QUEUE //buffer的数量
  };
  //设置PCM输出数据的格式
  SLDataFormat_PCM pcm = {
      SL_DATAFORMAT_PCM, //输出PCM格式的数据
      2,  //  //输出的声道数量
      SL_SAMPLINGRATE_48, //输出的采样频率
      SL_PCMSAMPLEFORMAT_FIXED_16, //输出的采样格式
      SL_PCMSAMPLEFORMAT_FIXED_16,//一般来说，跟随上一个参数
      SL_SPEAKER_FRONT_LEFT
          | SL_SPEAKER_FRONT_RIGHT,//双声道配置，如果单声道可以用 SL_SPEAKER_FRONT_CENTER
      SL_BYTEORDER_LITTLEENDIAN //PCM数据的大小端排列
  };
  // 输出
  SLDataSink dataSink = {
      &simpleBufferQueue, //PCM配置输出
      &pcm //输出数据格式
  };

  //创建录制的对象
  SLInterfaceID
      iIds[NUM_RECORDER_EXPLICIT_INTERFACES] = {SL_IID_ANDROIDSIMPLEBUFFERQUEUE,
                                                SL_IID_ANDROIDCONFIGURATION};
  SLboolean required[NUM_RECORDER_EXPLICIT_INTERFACES] =
      {SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE};

  // 创建 AudioRecorder 对象
  result = (*engineEngine)->CreateAudioRecorder(engineEngine, //引擎接口
                                                &recorderObject, //录制对象地址，用于传出对象
                                                &dataSource,//输入配置
                                                &dataSink,//输出配置
                                                NUM_RECORDER_EXPLICIT_INTERFACES,//支持的接口数量
                                                iIds, //具体的要支持的接口
                                                required //具体的要支持的接口是开放的还是关闭的
  );
  SL_ASSERT(result);

  //获取配置接口
  (*recorderObject)->GetInterface(recorderObject,
                                  SL_IID_ANDROIDCONFIGURATION,
                                  &configItf);
  SL_ASSERT(result);
  //实例化这个录制对象
  result = (*recorderObject)->Realize(recorderObject, SL_BOOLEAN_FALSE);
  SL_ASSERT(result);
  //获取buffer接口
  result = (*recorderObject)->GetInterface(recorderObject,
                                           SL_IID_ANDROIDSIMPLEBUFFERQUEUE,
                                           (void *) &recorderBuffQueueItf);
  SL_ASSERT(result);
  //获取录制接口
  (*recorderObject)->GetInterface(recorderObject,
                                  SL_IID_RECORD,
                                  &recorderRecord);
  SL_ASSERT(result);
  //设置数据回调并且开始录制，设置开始录制状态，并通过回调函数获取录制的音频PCM数据：
  recordBuffer = new uint8_t[bufferSize]; //数据缓存区
  recordBufferSize = bufferSize;
  //设置数据回调接口AudioRecorderCallback，最后一个参数是可以传输自定义的上下文引用
  (*recorderBuffQueueItf)->RegisterCallback(recorderBuffQueueItf,
                                            OnAudioRecorderCallback,
                                            this);
  result = (*recorderBuffQueueItf)->Clear(recorderBuffQueueItf);

  SL_ASSERT(result);
  // 开始录制音频，设置录制器为录制状态 RECORDING
  result = (*recorderRecord)->SetRecordState(recorderRecord,
                                             SL_RECORDSTATE_RECORDING);
  SL_ASSERT(result);

  // 在设置完录制状态后一定需要先Enqueue一次，这样的话才会开始采集回调
  (*recorderBuffQueueItf)->Enqueue(recorderBuffQueueItf,
                                   recordBuffer,
                                   bufferSize);
  SL_ASSERT(result);

  LOGD("StartRecording...");
}

void AudioRecorderOpenSLES::StopRecord() {
  // 停止录制
  if (recorderRecord != nullptr) {
    LOGI(TAG, "StopRecord...");
    //设置录制器为停止状态 STOPPED
    SLresult result = (*recorderRecord)->SetRecordState(
        recorderRecord, SL_RECORDSTATE_STOPPED
    );
    SL_ASSERT(result);
    delete recordBuffer;
    recordBuffer = nullptr;
    recorderRecord = nullptr;
  } else {
    LOGE("StopRecord...ignore");
  }
  if (nullptr != recorderObject) {
    (*recorderObject)->Destroy(recorderObject);
    recorderObject = nullptr;
  }
}

AudioRecorderOpenSLES::~AudioRecorderOpenSLES() {
  if (nullptr != engineObject) {
    (*engineObject)->Destroy(engineObject);
    engineObject = nullptr;
    engineEngine = nullptr;
    recorderBuffQueueItf = nullptr;
    configItf = nullptr;
  }
  LOGE("~AudioRecorderOpenSLES destructor");
}