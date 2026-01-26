//
// Created by cheerwizard on 17.10.25.
//

#include "../api/Vk.h"

#include <jni.h>

#ifdef ANDROID
#include <android/native_window_jni.h>
#include <vulkan/vulkan_android.h>
#endif

static JavaVM* vm = nullptr;
static jobject log_bridge_callback = nullptr;
static jobject result_bridge_callback = nullptr;

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage);
void ResultBridge_callback(VkResult result);

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_LogBridge_callback(
    JNIEnv* env, jobject /*thiz*/,
    jobject callback
) {
    env->GetJavaVM(&vm);
    log_bridge_callback = env->NewGlobalRef(callback);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_ResultBridge_callback(
    JNIEnv* env, jobject /*thiz*/,
    jobject callback
) {
    env->GetJavaVM(&vm);
    result_bridge_callback = env->NewGlobalRef(callback);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_removeCallbacks(
        JNIEnv* env, jobject /*thiz*/
) {
    vm = nullptr;
    log_bridge_callback = nullptr;
    result_bridge_callback = nullptr;
}

void LogBridge_callback(int level, const char* tag, const char* message, const char* exceptionMessage) {
    if (!vm || !log_bridge_callback) return;

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

    jclass clazz = env->GetObjectClass(log_bridge_callback);
    jmethodID invoke = env->GetMethodID(clazz, "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;");
    env->CallObjectMethod(log_bridge_callback, invoke, level, jtag, jmessage, jexception);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmessage);
    if (jexception) {
        env->DeleteLocalRef(jexception);
    }

    if (detach) {
        vm->DetachCurrentThread();
    }
}

void ResultBridge_callback(VkResult result) {
    if (!vm || !result_bridge_callback) return;

    JNIEnv* env = nullptr;
    bool detach = false;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        vm->AttachCurrentThread((void**) &env, nullptr);
        detach = true;
    }

    jclass clazz = env->GetObjectClass(result_bridge_callback);
    jmethodID invoke = env->GetMethodID(clazz, "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;");
    env->CallObjectMethod(result_bridge_callback, invoke, result);

    if (detach) {
        vm->DetachCurrentThread();
    }
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_create(
        JNIEnv* env,
        jclass,
        jobject surface,
        jobject infoBuffer) {

    void* nativeWindow = nullptr;

#ifdef ANDROID
    if (surface) {
        nativeWindow = ANativeWindow_fromSurface(env, surface);
    } else {
        LOG_ERROR("VK.jni", "Failed to obtain ANativeWindow, surface = null");
    }
#endif

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) {
        return 0;
    }

    auto* info = reinterpret_cast<VkContextInfo*>(ptr);
    VkContext* ctx = VkContext_create(nativeWindow, info);
    return reinterpret_cast<jlong>(ctx);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_destroy(
        JNIEnv*,
        jclass,
        jlong context) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    VkContext_destroy(ctx);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_wait(
        JNIEnv*,
        jclass,
        jlong context) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    VkContext_wait(ctx);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_resize(
        JNIEnv*,
        jclass,
        jlong context,
        jint width,
        jint height) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    VkContext_resize(ctx, width, height);
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_getRenderTarget(
        JNIEnv*,
        jclass,
        jlong context) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    return reinterpret_cast<jlong>(VkContext_getRenderTarget(ctx));
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_getPrimaryCommandBuffer(
        JNIEnv*,
        jclass,
        jlong context,
        jint frame) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    return reinterpret_cast<jlong>(
        VkContext_getPrimaryCommandBuffer(ctx, frame));
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_getSecondaryCommandBuffer(
        JNIEnv*,
        jclass,
        jlong context) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    return reinterpret_cast<jlong>(
        VkContext_getSecondaryCommandBuffer(ctx));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_beginFrame(
        JNIEnv*,
        jclass,
        jlong context,
        jint frame) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    VkContext_beginFrame(ctx, frame);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkContext_endFrame(
        JNIEnv*,
        jclass,
        jlong context,
        jint frame) {

    auto* ctx = reinterpret_cast<VkContext*>(context);
    VkContext_endFrame(ctx, frame);
}

// --------------------------------------------------
// VkShader
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkShader_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkShaderInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkShader_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkShader_destroy(
        JNIEnv*,
        jclass,
        jlong shader) {

    auto* s = reinterpret_cast<VkShader*>(shader);
    VkShader_destroy(s);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkShader_update(
        JNIEnv* env,
        jclass,
        jlong shader,
        jobject infoBuffer) {

    auto* s = reinterpret_cast<VkShader*>(shader);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return;

    auto* info = reinterpret_cast<VkShaderInfo*>(ptr);

    VkShader_update(s, info);
}

// --------------------------------------------------
// VkBindingLayout
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkBindingLayout_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkBindingInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkBindingLayout_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkBindingLayout_destroy(
        JNIEnv*,
        jclass,
        jlong layout) {

    auto* l = reinterpret_cast<VkBindingLayout*>(layout);
    VkBindingLayout_destroy(l);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkBindingLayout_update(
        JNIEnv* env,
        jclass,
        jlong layout,
        jobject infoBuffer) {

    auto* l = reinterpret_cast<VkBindingLayout*>(layout);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return;

    auto* info = reinterpret_cast<VkBindingInfo*>(ptr);

    VkBindingLayout_update(l, info);
}

// --------------------------------------------------
// VkRenderTarget
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkRenderTarget_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkRenderTargetInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkRenderTarget_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkRenderTarget_destroy(
        JNIEnv*,
        jclass,
        jlong target) {

    auto* t = reinterpret_cast<VkRenderTarget*>(target);
    VkRenderTarget_destroy(t);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkRenderTarget_resize(
        JNIEnv*,
        jclass,
        jlong target,
        jint width,
        jint height) {

    auto* t = reinterpret_cast<VkRenderTarget*>(target);
    VkRenderTarget_resize(t, width, height);
}

// --------------------------------------------------
// VkBufferResource
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkBufferResource_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkBufferInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkBufferResource_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkBufferResource_destroy(
        JNIEnv*,
        jclass,
        jlong buffer) {

    auto* b = reinterpret_cast<VkBufferResource*>(buffer);
    VkBufferResource_destroy(b);
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkBufferResource_map(
        JNIEnv*,
        jclass,
        jlong buffer) {

    auto* b = reinterpret_cast<VkBufferResource*>(buffer);
    return reinterpret_cast<jlong>(
        VkBufferResource_map(b));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkBufferResource_unmap(
        JNIEnv*,
        jclass,
        jlong buffer) {

    auto* b = reinterpret_cast<VkBufferResource*>(buffer);
    VkBufferResource_unmap(b);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkBufferResource_updateBinding(
        JNIEnv*,
        jclass,
        jlong buffer,
        jint frame) {

    auto* b = reinterpret_cast<VkBufferResource*>(buffer);
    VkBufferResource_updateBinding(b, frame);
}

// --------------------------------------------------
// VkSamplerResource
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkSamplerResource_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkSamplerInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkSamplerResource_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkSamplerResource_destroy(
        JNIEnv*,
        jclass,
        jlong sampler) {

    auto* s = reinterpret_cast<VkSamplerResource*>(sampler);
    VkSamplerResource_destroy(s);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkSamplerResource_updateBinding(
        JNIEnv*,
        jclass,
        jlong sampler,
        jint frame) {

    auto* s = reinterpret_cast<VkSamplerResource*>(sampler);
    VkSamplerResource_updateBinding(s, frame);
}

// --------------------------------------------------
// VkTextureResource
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkTextureResource_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkTextureInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkTextureResource_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkTextureResource_destroy(
        JNIEnv*,
        jclass,
        jlong tex) {

    auto* t = reinterpret_cast<VkTextureResource*>(tex);
    VkTextureResource_destroy(t);
}

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkTextureResource_map(
        JNIEnv*,
        jclass,
        jlong tex) {

    auto* t = reinterpret_cast<VkTextureResource*>(tex);
    return reinterpret_cast<jlong>(
        VkTextureResource_map(t));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkTextureResource_unmap(
        JNIEnv*,
        jclass,
        jlong tex) {

    auto* t = reinterpret_cast<VkTextureResource*>(tex);
    VkTextureResource_unmap(t);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkTextureResource_updateBinding(
        JNIEnv*,
        jclass,
        jlong tex,
        jint frame) {

    auto* t = reinterpret_cast<VkTextureResource*>(tex);
    VkTextureResource_updateBinding(t, frame);
}

// --------------------------------------------------
// VkPipe
// --------------------------------------------------

JNIEXPORT jlong JNICALL
Java_com_cws_kanvas_vk_VK_VkPipe_create(
        JNIEnv* env,
        jclass,
        jlong context,
        jobject infoBuffer) {

    auto* ctx = reinterpret_cast<VkContext*>(context);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return 0;

    auto* info = reinterpret_cast<VkPipeInfo*>(ptr);

    return reinterpret_cast<jlong>(
        VkPipe_create(ctx, info));
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkPipe_destroy(
        JNIEnv*,
        jclass,
        jlong pipe) {

    auto* p = reinterpret_cast<VkPipe*>(pipe);
    VkPipe_destroy(p);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkPipe_update(
        JNIEnv* env,
        jclass,
        jlong pipe,
        jobject infoBuffer) {

    auto* p = reinterpret_cast<VkPipe*>(pipe);

    void* ptr = env->GetDirectBufferAddress(infoBuffer);
    if (!ptr) return;

    auto* info = reinterpret_cast<VkPipeInfo*>(ptr);

    VkPipe_update(p, info);
}

// --------------------------------------------------
// VkCommandBufferResource
// --------------------------------------------------

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_reset(
        JNIEnv*,
        jclass,
        jlong cmd) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    VkCommandBufferResource_reset(c);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_begin(
        JNIEnv*,
        jclass,
        jlong cmd) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    VkCommandBufferResource_begin(c);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_end(
        JNIEnv*,
        jclass,
        jlong cmd) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    VkCommandBufferResource_end(c);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_beginRenderPass(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong renderTarget,
        jint colorAttachmentIndex) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* rt = reinterpret_cast<VkRenderTarget*>(renderTarget);

    VkCommandBufferResource_beginRenderPass(c, rt, colorAttachmentIndex);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_endRenderPass(
        JNIEnv*,
        jclass,
        jlong cmd) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    VkCommandBufferResource_endRenderPass(c);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_setPipe(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong pipe) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* p = reinterpret_cast<VkPipe*>(pipe);

    VkCommandBufferResource_setPipe(c, p);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_setViewport(
        JNIEnv*,
        jclass,
        jlong cmd,
        jfloat x,
        jfloat y,
        jfloat width,
        jfloat height,
        jfloat minDepth,
        jfloat maxDepth) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);

    VkCommandBufferResource_setViewport(
        c, x, y, width, height, minDepth, maxDepth);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_setScissor(
        JNIEnv*,
        jclass,
        jlong cmd,
        jint x,
        jint y,
        jint w,
        jint h) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    VkCommandBufferResource_setScissor(c, x, y, w, h);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_addSecondaryBuffer(
        JNIEnv*,
        jclass,
        jlong commandBuffer,
        jlong secondaryBuffer) {

    auto* cmd = reinterpret_cast<VkCommandBufferResource*>(commandBuffer);
    auto* secondary = reinterpret_cast<VkCommandBufferResource*>(secondaryBuffer);
    VkCommandBufferResource_addSecondaryBuffer(cmd, secondary);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_addSecondaryBuffers(
        JNIEnv* env,
        jclass,
        jlong commandBuffer,
        jlongArray secondaryBuffers) {

    auto* cmd = reinterpret_cast<VkCommandBufferResource*>(commandBuffer);

    jsize count = env->GetArrayLength(secondaryBuffers);
    jlong* elements = env->GetLongArrayElements(secondaryBuffers, nullptr);

    std::vector<VkCommandBufferResource*> buffers(count);
    for (jsize i = 0; i < count; ++i) {
        buffers[i] = reinterpret_cast<VkCommandBufferResource*>(elements[i]);
    }

    VkCommandBufferResource_addSecondaryBuffers(cmd, buffers.data(), static_cast<size_t>(count));

    env->ReleaseLongArrayElements(secondaryBuffers, elements, 0);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_draw(
        JNIEnv*,
        jclass,
        jlong cmd,
        jint vertices,
        jint vertexOffset,
        jint instances,
        jint instanceOffset) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);

    VkCommandBufferResource_draw(
        c, vertices, vertexOffset, instances, instanceOffset);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_drawIndexed(
        JNIEnv*,
        jclass,
        jlong cmd,
        jint vertices,
        jint vertexOffset,
        jint indices,
        jint indexOffset,
        jint instances,
        jint instanceOffset) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);

    VkCommandBufferResource_drawIndexed(
        c, vertices, vertexOffset, indices,
        indexOffset, instances, instanceOffset);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_drawIndexedIndirect(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong indirectBuffer,
        jlong offset,
        jint drawCount) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* b = reinterpret_cast<VkBufferResource*>(indirectBuffer);

    VkCommandBufferResource_drawIndexedIndirect(
        c, b, offset, drawCount);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_copyBufferToBuffer(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong src,
        jlong dst,
        jlong srcOffset,
        jlong dstOffset,
        jlong size) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* s = reinterpret_cast<VkBufferResource*>(src);
    auto* d = reinterpret_cast<VkBufferResource*>(dst);

    VkCommandBufferResource_copyBufferToBuffer(
        c, s, d, srcOffset, dstOffset, size);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_copyBufferToImage(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong src,
        jlong dst,
        jint mip,
        jint w,
        jint h,
        jint dpth) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* s = reinterpret_cast<VkBufferResource*>(src);
    auto* t = reinterpret_cast<VkTextureResource*>(dst);

    VkCommandBufferResource_copyBufferToImage(
        c, s, t, mip, w, h, dpth);
}

JNIEXPORT void JNICALL
Java_com_cws_kanvas_vk_VK_VkCommandBufferResource_copyImageToImage(
        JNIEnv*,
        jclass,
        jlong cmd,
        jlong src,
        jlong dst,
        jint sx,
        jint sy,
        jint sz,
        jint dx,
        jint dy,
        jint dz,
        jint w,
        jint h,
        jint dpth) {

    auto* c = reinterpret_cast<VkCommandBufferResource*>(cmd);
    auto* s = reinterpret_cast<VkTextureResource*>(src);
    auto* d = reinterpret_cast<VkTextureResource*>(dst);

    VkCommandBufferResource_copyImageToImage(
        c, s, d,
        sx, sy, sz,
        dx, dy, dz,
        w, h, dpth);
}
