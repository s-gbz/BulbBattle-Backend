#include "hue.h"
#include <hueplusplus/Hue.h>
#include <hueplusplus/LinHttpHandler.h>

#include <iostream>
#include <unistd.h>


JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_switchOn
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId)
{
  HueLight light = get(bridgeIp, bridgeUsername, lightId);
  light.On();
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_switchOff
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId)
{
  HueLight light = get(bridgeIp, bridgeUsername, lightId);
  light.Off();
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_setColor
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId, jint r, jint g, jint b)
{
  HueLight light = get(bridgeIp, bridgeUsername, lightId);
  light.setColorRGB(r, g, b);
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_setBrightness
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId, jint brightness)
{
  HueLight light = get(bridgeIp, bridgeUsername, lightId);
  light.setBrightness(brightness)
}

HueLight get(jstring bridgeIp, jstring bridgeUsername, jlong lightId) {
  const char* pIp = env->GetStringUTFChars(ip, NULL);
  const char* pUsername = env->GetStringUTFChars(username, NULL);  
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  return bridge.getLight(lightId);
}
