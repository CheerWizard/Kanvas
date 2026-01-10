//
// Created by cheerwizard on 07.11.25.
//

#ifndef STC_SHADERCOMPILER_HPP
#define STC_SHADERCOMPILER_HPP

#include "backend/Shader.hpp"

namespace stc {

    struct ShaderCompiler {

        ShaderSource compile(const ShaderSource& source, ShaderSourceType fromType, ShaderSourceType toType);

    private:
        std::vector<u32> glslToSPIRV(const std::string& text, ShaderStage stage);
        std::string spirvToWGSL(const std::vector<u32>& spirv);
        std::string spirvToMSL(const std::vector<u32>& spirv);
        std::string spirvToHLSL(const std::vector<u32>& spirv);

        inline static auto TAG = "ShaderCompiler";
    };

}

#endif //STC_SHADERCOMPILER_HPP