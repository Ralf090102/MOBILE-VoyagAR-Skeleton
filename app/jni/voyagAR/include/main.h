//
// Created by Kev on 12/03/2025.
//

#ifndef MOBILE_VOYAGAR_SKELETON_MAIN_H
#define MOBILE_VOYAGAR_SKELETON_MAIN_H
#define LOG_TAG "VoyagAR"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


#include <SDL3/SDL.h>
#include <SDL3/SDL_vulkan.h>
#include <jni.h>
#include <android/log.h>

// Define log macros
#
// Function declarations
bool initSDL();
void cleanupSDL();
void renderFrame();

#endif //MOBILE_VOYAGAR_SKELETON_MAIN_H
