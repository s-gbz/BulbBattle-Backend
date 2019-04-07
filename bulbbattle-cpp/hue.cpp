#include "hue.h"
#include <hueplusplus/Hue.h>
#include <hueplusplus/LinHttpHandler.h>

#include <iostream>
#include <unistd.h>

HueLight get(JNIEnv *env, jstring bridgeIp, jstring bridgeUsername, jlong lightId) {
  const char* pIp = env->GetStringUTFChars(bridgeIp, NULL);
  const char* pUsername = env->GetStringUTFChars(bridgeUsername, NULL);  
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  return bridge.getLight(lightId);
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_LightService_switchOn
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId)
{
  HueLight light = get(env, bridgeIp, bridgeUsername, lightId);
  light.On();
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_LightService_switchOff
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId)
{
  HueLight light = get(env, bridgeIp, bridgeUsername, lightId);
  light.Off();
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_LightService_setColor
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId, jint r, jint g, jint b)
{
  HueLight light = get(env, bridgeIp, bridgeUsername, lightId);
  light.setColorRGB(r, g, b);
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_LightService_setBrightness
  (JNIEnv *env, jobject thisObject, jstring bridgeIp, jstring bridgeUsername, jlong lightId, jint brightness)
{
  HueLight light = get(env, bridgeIp, bridgeUsername, lightId);
  light.setBrightness(brightness);
}
