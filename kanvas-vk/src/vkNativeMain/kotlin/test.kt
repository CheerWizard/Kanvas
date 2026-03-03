import kotlinx.cinterop.ExperimentalForeignApi
import vk.VkBinding
import vk.VkContext_create
import vk.VkContext_destroy
import vk.VkImage

@OptIn(ExperimentalForeignApi::class)
fun test() {
    val context: Any? = VkContext_create(null, null)
    VkContext_destroy(context)
    val image: VkImage
    VkBinding
}