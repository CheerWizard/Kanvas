//
// Created by cheerwizard on 08.11.25.
//

#ifndef STC_SHADERMANAGER_HPP
#define STC_SHADERMANAGER_HPP

#include "ShaderCompiler.hpp"

namespace stc {

    struct ShaderManager {

        ShaderManager(Device& device);
        ~ShaderManager();

        Ptr<Shader> addShader(const std::string& name, const ShaderSource& source);
        Ptr<Shader> removeShader(const std::string& name);
        Ptr<Shader> getShader(const std::string& name);

        ShaderSource compile(const ShaderSource& source, ShaderSourceType fromType, ShaderSourceType toType);

    private:
        Device& device;
        std::unordered_map<std::string, Ptr<Shader>> shaders;
        ShaderCompiler compiler;
    };

}

#endif //STC_SHADERMANAGER_HPP