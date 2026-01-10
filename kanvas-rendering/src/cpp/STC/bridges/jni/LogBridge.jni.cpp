//
// Created by cheerwizard on 17.10.25.
//

#include "../LogBridge.hpp"

#include <jni.h>

using namespace stc;

static JavaVM* vm = nullptr;
static jclass log_class = nullptr;
static jmethodID log_method = nullptr;

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage);

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_gfx_bridges_LogBridge_nativeInit(
        JNIEnv* env, jobject /*thiz*/,
        jstring className,
        jstring methodName,
        jstring methodSignature
) {
    env->GetJavaVM(&vm);
    jclass clazz = env->FindClass(env->GetStringUTFChars(className, nullptr));
    log_class = reinterpret_cast<jclass>(env->NewGlobalRef(clazz));
    log_method = env->GetStaticMethodID(
        log_class,
        env->GetStringUTFChars(methodName, nullptr),
        env->GetStringUTFChars(methodSignature, nullptr)
    );
    LogBridge_init(LogBridge_callback);
}

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage) {
    if (!vm || !log_class || !log_method) return;

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

    env->CallStaticVoidMethod(log_class, log_method, level, jtag, jmessage, jexception);
    env->DeleteLocalRef(jmessage);

    if (detach) {
        vm->DetachCurrentThread();
    }
}