package com.cws.kanvas.rendering.backend

import com.cws.kanvas.vk.VK
import com.cws.kanvas.vk.VkBinding
import com.cws.kanvas.vk.VkBindingInfo
import com.cws.kanvas.vk.VkBindingType
import com.cws.std.memory.CString
import com.cws.std.memory.NativeDataList

actual enum class BindingType(val value: VkBindingType) {
    UNIFORM_BUFFER(VkBindingType.VK_BINDING_TYPE_UNIFORM_BUFFER),
    STORAGE_BUFFER(VkBindingType.VK_BINDING_TYPE_STORAGE_BUFFER),
    TEXTURE(VkBindingType.VK_BINDING_TYPE_TEXTURE),
    SAMPLER(VkBindingType.VK_BINDING_TYPE_SAMPLER),
}

actual typealias BindingLayoutHandle = Long

actual class BindingLayout actual constructor(
    private val renderContext: RenderContext,
    actual val info: BindingLayoutInfo,
) : Resource<BindingLayoutHandle>() {

    private val vkInfo = VkBindingInfo(
        name = CString(info.name),
        bindings = NativeDataList(info.bindings.map { binding ->
                VkBinding(
                    type = binding.type.value,
                    shaderStages = binding.shaderStages,
                    set = binding.set,
                    binding = binding.binding,
                    count = binding.count,
                )
            }.toMutableList(),
        ),
    )

    actual override fun onCreate() {
        renderContext.handle?.let { ctx ->
            vkInfo.pack()?.let {
                VK.VkBindingLayout_create(ctx, it.buffer)
            }
        }
    }

    actual override fun onDestroy() {
        handle?.let {
            VK.VkBindingLayout_destroy(it)
        }
    }

    actual fun update() {
        handle?.let { handle ->
            vkInfo.bindings.list.clear()
            info.bindings.forEach { binding ->
                vkInfo.bindings.list.add(VkBinding(
                    type = binding.type.value,
                    shaderStages = binding.shaderStages,
                    set = binding.set,
                    binding = binding.binding,
                    count = binding.count,
                ))
            }
            vkInfo.pack()?.let { info ->
                VK.VkBindingLayout_update(handle, info.buffer)
            }
        }
    }

}