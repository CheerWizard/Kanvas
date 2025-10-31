#include "../RenderBridge.hpp"

#include <jni.h>
#include <jni.h>

static stc::RenderBridge* render_bridge = nullptr;

#ifdef ANDROID

#include <vulkan/vulkan_android.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeInit(
        JNIEnv* env, jobject /*thiz*/,
        jobject surface,
        jint width,
        jint height
) {
    ANativeWindow* nativeWindow = ANativeWindow_fromSurface(env, surface);
    render_bridge = new stc::RenderBridge({
        .nativeWindow = nativeWindow,
        .width = width,
        .height = height,
    });
}

#endif // ANDROID

#ifdef DESKTOP

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeInit(
        JNIEnv* env, jobject /*thiz*/,
        jobject nativeWindow,
        jint width,
        jint height
) {
    render_bridge = new stc::RenderBridge({
        .nativeWindow = nativeWindow,
        .width = width,
        .height = height,
    });
}

#endif // DESKTOP

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeFree(
        JNIEnv* env, jobject /*thiz*/
) {
    delete render_bridge;
    render_bridge = nullptr;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeResize(
        JNIEnv* env, jobject /*thiz*/,
        jint width,
        jint height
) {
    render_bridge->resize(width, height);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeBeginFrame(
        JNIEnv* env, jobject /*thiz*/
) {
    render_bridge->beginFrame();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeEndFrame(
        JNIEnv* env, jobject /*thiz*/
) {
    render_bridge->endFrame();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeRegisterPixels(
        JNIEnv* env, jobject /*thiz*/,
        jobject pixels
) {

}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_RenderBridge_nativeUnregisterPixels(
        JNIEnv* env, jobject /*thiz*/
) {

}