//
// Created by cheerwizard on 08.11.25.
//

#include "ShaderManager.hpp"

namespace stc {

    ShaderManager::ShaderManager(Device &device) : device(device) {}

    ShaderManager::~ShaderManager() {
        for (auto& shader : shaders | std::views::values) {
            shader.Delete();
        }
        shaders.clear();
    }

    Ptr<Shader> ShaderManager::addShader(const std::string &name, const ShaderSource& source) {
        Ptr<Shader> shader;

        if (shaders.contains(name)) {
            shader = shaders[name];
        } else {
            shader.New(device, ShaderCreateInfo {
                .name = name,
                .entryPoint = "main",
                .source = source,
            });
        }

        return shader;
    }

    Ptr<Shader> ShaderManager::removeShader(const std::string &name) {
    }

    Ptr<Shader> ShaderManager::getShader(const std::string &name) {
    }

}
