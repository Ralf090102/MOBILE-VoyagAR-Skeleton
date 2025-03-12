#include "main.h"
#include <string>
#include <android/native_window.h>
#include <android/native_window_jni.h>

// Global variables
SDL_Window* window = NULL;
SDL_Renderer* renderer = NULL;
bool isRunning = false;

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeInit(JNIEnv* env, jobject thiz) {
    LOGI("Native initialization");
    initSDL();
}

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeSurfaceCreated(JNIEnv* env, jobject thiz, jobject surface) {
    LOGI("Surface created");

    // Get the native window from the Java surface
    ANativeWindow* nativeWindow = ANativeWindow_fromSurface(env, surface);
    if (!nativeWindow) {
        LOGE("Could not get native window from surface");
        return;
    }

    // For SDL3 on Android, we need a more basic window creation approach
    // Direct creation from native window using simplified approach
    window = SDL_CreateWindow("MyTitle", 640, 480, SDL_WINDOW_RESIZABLE);
    if (!window) {
        LOGE("Could not create window: %s", SDL_GetError());
        ANativeWindow_release(nativeWindow);
        return;
    }

    if (!window) {
        LOGE("Could not create window: %s", SDL_GetError());
        ANativeWindow_release(nativeWindow);
        return;
    }

    // Create renderer
    renderer = SDL_CreateRenderer(window, nullptr );
    if (!renderer) {
        LOGE("Could not create renderer: %s", SDL_GetError());
        SDL_DestroyWindow(window);
        window = NULL;
        ANativeWindow_release(nativeWindow);
        return;
    }

    isRunning = true;
    LOGI("SDL renderer created successfully");
}

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeRender(JNIEnv* env, jobject thiz) {
    if (isRunning) {
        renderFrame();
    }
}

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeSurfaceChanged(JNIEnv* env, jobject thiz, jint width, jint height) {
    LOGI("Surface changed: %d x %d", width, height);
}

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeSurfaceDestroyed(JNIEnv* env, jobject thiz) {
    LOGI("Surface destroyed");
    cleanupSDL();
}

JNIEXPORT void JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_nativeQuit(JNIEnv* env, jobject thiz) {
    LOGI("Native quit");
    cleanupSDL();
}

// Keep the original stringFromJNI function for compatibility
JNIEXPORT jstring JNICALL
Java_com_example_mobile_1voyagar_1skeleton_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */) {
    return env->NewStringUTF("Hello from SDL and Native Code");
}

} // extern "C"

bool initSDL() {
    if (SDL_Init(SDL_INIT_VIDEO) != 0) {
        LOGE("SDL could not initialize! SDL_Error: %s", SDL_GetError());
        return false;
    }

    LOGI("SDL initialized successfully");
    return true;
}

void cleanupSDL() {
    if (renderer) {
        SDL_DestroyRenderer(renderer);
        renderer = NULL;
    }

    if (window) {
        SDL_DestroyWindow(window);
        window = NULL;
    }

    isRunning = false;

    // Quit SDL subsystems
    SDL_Quit();

    LOGI("SDL cleaned up");
}

void renderFrame() {
    if (!renderer) return;

    // Clear screen with a blue color
    SDL_SetRenderDrawColor(renderer, 0, 100, 255, 255);
    SDL_RenderClear(renderer);

    // Render a white rectangle in the center
    SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    SDL_Rect rect;
    int width, height;
    SDL_GetWindowSize(window, &width, &height);
    rect.x = width / 4;
    rect.y = height / 4;
    rect.w = width / 2;
    rect.h = height / 2;
    // Convert SDL_Rect to SDL_FRect for SDL3
    SDL_FRect frect;
    frect.x = (float)rect.x;
    frect.y = (float)rect.y;
    frect.w = (float)rect.w;
    frect.h = (float)rect.h;
    SDL_RenderFillRect(renderer, &frect);

    // Present the renderer
    SDL_RenderPresent(renderer);
}