#include "../RenderApiBridge.hpp"

// Render API exposed to Kotlin JVM

#include <jni.h>

using namespace stc;

#ifdef ANDROID

#include <vulkan/vulkan_android.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeInit(
        JNIEnv* env, jobject /*thiz*/,
        jobject surface,
        jint width,
        jint height
) {
    ANativeWindow* nativeWindow = ANativeWindow_fromSurface(env, surface);
    RenderApiBridge_init(RenderConfig {
        .nativeWindow = nativeWindow,
        .width = width,
        .height = height,
    });
}

#endif // ANDROID

#ifdef DESKTOP

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeInit(
        JNIEnv* env, jobject /*thiz*/,
        jobject nativeWindow,
        jint width,
        jint height
) {
    RenderApiBridge_init(RenderConfig {
        .nativeWindow = nativeWindow,
        .width = (u32) width,
        .height = (u32) height,
    });
}

#endif // DESKTOP

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeFree(
        JNIEnv* env, jobject /*thiz*/
) {
    RenderApiBridge_release();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeResize(
        JNIEnv* env, jobject /*thiz*/,
        jint width,
        jint height
) {
    RenderApiBridge_resize(width, height);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeBeginFrame(
        JNIEnv* env, jobject /*thiz*/
) {
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeEndFrame(
        JNIEnv* env, jobject /*thiz*/
) {
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeRegisterPixels(
        JNIEnv* env, jobject /*thiz*/,
        jobject pixels
) {

}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderApiBridge_nativeUnregisterPixels(
        JNIEnv* env, jobject /*thiz*/
) {

}