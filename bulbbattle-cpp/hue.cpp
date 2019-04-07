#include "hue.h"
#include <hueplusplus/Hue.h>
#include <hueplusplus/LinHttpHandler.h>

#include <iostream>
#include <unistd.h>

struct Color {
	long r, g, b;
};

Color red = { 244, 67, 54 };
Color green = { 76, 175, 80 };
Color blue = { 33, 150, 243 };
Color yellow = { 255, 235, 59 };

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

  int i = 0;
  jsize len = env->GetArrayLength(colorSequence);
  jint *body = env->GetIntArrayElements(colorSequence, 0);

  for (i=0; i<len; i++) {
    light.On();

    if(body[i] == 0) {
      light.setColorRGB(red.r, red.g, red.b);
    } else if(body[i] == 1) {
      light.setColorRGB(green.r, green.g, green.b);
    } else if(body[i] == 2) {
      light.setColorRGB(blue.r, blue.g, blue.b);
    } else if(body[i] == 3) {
      light.setColorRGB(yellow.r, yellow.g, yellow.b);
    }

    usleep(duration);
    light.Off();
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