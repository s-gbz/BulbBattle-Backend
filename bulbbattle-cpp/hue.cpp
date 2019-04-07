#include "hue.h"
#include <hueplusplus/Hue.h>
#include <hueplusplus/LinHttpHandler.h>

#include <iostream>
#include <unistd.h>

struct Color {
	long r, g, b;
};

// 0: RED
// 1: GREEN
// 2: BLUE
// 3: YELLOW
Color colors[4] = {{ 244, 67, 54 }, { 76, 175, 80 }, { 33, 150, 243 }, { 255, 235, 59 }};

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_roundPause
  (JNIEnv * env, jobject thisObject, jstring ip, jstring username, jlong lightId, jlong duration)
{
  const char* pIp = env->GetStringUTFChars(ip, NULL);
  const char* pUsername = env->GetStringUTFChars(username, NULL);
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  HueLight light = bridge.getLight(lightId);
  light.setColorRGB(255, 255, 255);
  usleep(duration);
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_roundStart
  (JNIEnv * env, jobject thisObject, jstring ip, jstring username, jlong lightId, jintArray colorSequence, jlong duration)
{
  const char* pIp = env->GetStringUTFChars(ip, NULL);
  const char* pUsername = env->GetStringUTFChars(username, NULL);
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  HueLight light = bridge.getLight(lightId);
  light.setColorRGB(colors[0].r, colors[0].g, colors[0].b);

  int i = 0;
  jsize len = (*env)->GetArrayLength(env, colorSequence);
  jint *body = (*env)->GetIntArrayElements(env, colorSequence, 0);
  for (i=0; i<len; i++) {
      light.setColorRGB(colors[body[i]].r, colors[body[i]].g, colors[body[i]].b);
      usleep(duration);
      light.setColorRGB(0, 0, 0);
      usleep(duration);
  }
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_gamePause
  (JNIEnv * env, jobject thisObject, jstring ip, jstring username, jlong lightId)
{
  const char* pIp = env->GetStringUTFChars(ip, NULL);
  const char* pUsername = env->GetStringUTFChars(username, NULL);
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  HueLight light = bridge.getLight(lightId);
  light.Off();
}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_gameStart
  (JNIEnv * env, jobject thisObject, jstring ip, jstring username, jlong lightId, jlong duration)
{
  const char* pIp = env->GetStringUTFChars(ip, NULL);
  const char* pUsername = env->GetStringUTFChars(username, NULL);  
  auto handler = std::make_shared<LinHttpHandler>();
  Hue bridge(pIp, pUsername, handler);
  HueLight light = bridge.getLight(lightId);
  light.On();
  light.setColorRGB(255, 255, 255);
  usleep(duration);
}