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
  (JNIEnv *, jobject thisObject, jobject duration)
{

}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_roundStart
  (JNIEnv * env, jobject thisObject, jstring colorSequence, jobject duration)
{

}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_gamePause
  (JNIEnv * env, jobject thisObject)
{

}

JNIEXPORT void JNICALL Java_de_trzpiot_bulbbattle_service_NativeService_gameStart
  (JNIEnv * env, jobject thisObject)
{
  
}