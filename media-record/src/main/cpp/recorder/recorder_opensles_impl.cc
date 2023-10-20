//
//
#include "assert.h"
#include "android_debug.h"
#include "recorder_opensles_impl.h"

AudioRecorderOpenSLES::AudioRecorderOpenSLES() {
  // 创建引擎对象，调用全局方法创建一个引擎对象（OpenSL ES唯一入口）
  SLresult result;
  //1、创建引擎
  result = slCreateEngine(&engineObject,
                          0,
                          nullptr, //配置参数
                          0, //支持的接口数量
                          nullptr, //具体的要支持的接口，是枚举的数组
                          nullptr);
  assert(SL_RESULT_SUCCESS == result);
  //2、实例化引擎
  result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
  assert(SL_RESULT_SUCCESS == result);
  //3、获取引擎接口，这是创建其他对象所需要的
  result = (*engineObject)->GetInterface(engineObject,
                                         SL_IID_ENGINE,
                                         &engineEngine);
  assert(SL_RESULT_SUCCESS == result);
  LOGI("AudioRecorderOpenSLES constructor");
}

AudioRecorderOpenSLES::~AudioRecorderOpenSLES() {

  LOGE("~AudioRecorderOpenSLES destructor");
}