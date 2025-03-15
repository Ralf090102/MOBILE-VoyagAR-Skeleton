#include <SDL.h>
#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_initSDL(JNIEnv* env,jobject) {
    if (SDL_Init(SDL_INIT_VIDEO) != 0) {
        return env->NewStringUTF(SDL_GetError());
    }
    return env->NewStringUTF("SDL Initialized Successfully");
}