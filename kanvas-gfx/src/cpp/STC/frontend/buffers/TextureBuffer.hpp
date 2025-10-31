//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_TEXTURE_BUFFER_HPP
#define STC_TEXTURE_BUFFER_HPP

#include "backend/Binding.hpp"
#include "backend/Texture.hpp"

#define TEXTURE_INDEX_NULL UINT32_MAX

namespace stc {

    struct TextureBuffer {
        BindingLayout binding_layout;
        BindingSetPool binding_set_pool;
        BindingSet binding_set;
        std::vector<Scope<Texture>> textures;
        LockFreeIndices freeIndices;
        Scope<Sampler> sampler;
        u32 currentTextureIndex = TEXTURE_INDEX_NULL;

        TextureBuffer(const Device& device);
        ~TextureBuffer();
        u32 allocate(const TextureCreateInfo& create_info);
        void free(u32 textureIndex);

    private:
        static constexpr auto TAG = "TextureBuffer";
    };

}

#endif //STC_TEXTURE_BUFFER_HPP