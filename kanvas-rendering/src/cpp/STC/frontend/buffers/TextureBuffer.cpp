//
// Created by cheerwizard on 18.10.25.
//

#include "TextureBuffer.hpp"

namespace stc {

    TextureBuffer::TextureBuffer(Device &device) :
    device(device),
    binding_layout(device, {
        Binding {
            .type = BINDING_TYPE_TEXTURE,
            .slot = 0,
            .shader_stages = SHADER_STAGE_FRAGMENT,
        }
    }) {}

    TextureBuffer::~TextureBuffer() {
        textures.clear();
    }

    u32 TextureBuffer::allocate(const TextureCreateInfo& create_info) {
        u32 textureIndex = freeIndices.pop();
        if (textureIndex != TEXTURE_INDEX_NULL) {
            return textureIndex;
        }

        textures.emplace_back(device, create_info);

        return textures.size() - 1;
    }

    void TextureBuffer::free(u32 textureIndex) {
        if (textureIndex == TEXTURE_INDEX_NULL || textureIndex >= textures.size()) {
            LOG_WARNING(TAG, "Failed to free invalid texture index %i", textureIndex);
            return;
        }
        textures[textureIndex].Delete();
        freeIndices.push(textureIndex);
    }

}