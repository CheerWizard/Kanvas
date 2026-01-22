//
// Created by cheerwizard on 17.10.25.
//

#include "../bridges/LogBridge.hpp"
#include "../bridges/ResultBridge.hpp"

#include <jni.h>

#ifdef ANDROID
#include <vulkan/vulkan_android.h>
#endif

static JavaVM* vm = nullptr;
static jclass g_clazz = nullptr;
static jmethodID log_bridge_method = nullptr;
static jmethodID result_bridge_method = nullptr;

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage);
void ResultBridge_callback(VkResult result);

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_create(
        JNIEnv* env, jobject /*thiz*/,
        jstring className,
        jstring logBridgeMethodName,
        jstring logBridgeMethodSignature,
        jstring resultBridgeMethodName,
        jstring resultBridgeMethodSignature,
        jobject surface
) {
    // init globally class
    env->GetJavaVM(&vm);
    jclass clazz = env->FindClass(env->GetStringUTFChars(className, nullptr));
    g_clazz = reinterpret_cast<jclass>(env->NewGlobalRef(clazz));

    // init log bridge
    log_bridge_method = env->GetStaticMethodID(
            g_clazz,
            env->GetStringUTFChars(logBridgeMethodName, nullptr),
            env->GetStringUTFChars(logBridgeMethodSignature, nullptr)
    );
    LogBridge_init(LogBridge_callback);

    // init result bridge
    result_bridge_method = env->GetStaticMethodID(
            g_clazz,
            env->GetStringUTFChars(resultBridgeMethodName, nullptr),
            env->GetStringUTFChars(resultBridgeMethodSignature, nullptr)
    );
    ResultBridge_init(ResultBridge_callback);

    // init context
    void* nativeWindow = nullptr;

#ifdef ANDROID
    nativeWindow = ANativeWindow_fromSurface(env, surface);
#endif

}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_destroy(
        JNIEnv* env, jobject /*thiz*/
) {
    env->DeleteGlobalRef(g_clazz);
    vm = nullptr;
    g_clazz = nullptr;
    log_bridge_method = nullptr;
    result_bridge_method = nullptr;
}

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage) {
    if (!vm || !g_clazz || !log_bridge_method) return;

    JNIEnv* env = nullptr;
    bool detach = false;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        vm->AttachCurrentThread((void**) &env, nullptr);
        detach = true;
    }

    jstring jtag = env->NewStringUTF(tag);
    jstring jmessage = env->NewStringUTF(message);
    jstring jexception = nullptr;
    if (exceptionMessage) {
        jexception = env->NewStringUTF(exceptionMessage);
    }

    env->CallStaticVoidMethod(g_clazz, log_bridge_method, level, jtag, jmessage, jexception);
    env->DeleteLocalRef(jmessage);

    if (detach) {
        vm->DetachCurrentThread();
    }
}

void ResultBridge_callback(VkResult result) {
    if (!vm || !g_clazz || !result_bridge_method) return;

    JNIEnv* env = nullptr;
    bool detach = false;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        vm->AttachCurrentThread((void**) &env, nullptr);
        detach = true;
    }

    env->CallStaticVoidMethod(g_clazz, result_bridge_method, result);

    if (detach) {
        vm->DetachCurrentThread();
    }
}