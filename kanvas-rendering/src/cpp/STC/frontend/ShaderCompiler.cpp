//
// Created by cheerwizard on 07.11.25.
//

#include "ShaderCompiler.hpp"

#include "cmake-build-release/_deps/glslang-src/SPIRV/GlslangToSpv.h"

#include "glslang/Public/ResourceLimits.h"
#include "glslang/Public/ShaderLang.h"

#include "src/tint/lang/spirv/reader/reader.h"
#include "src/tint/lang/spirv/writer/writer.h"
#include "src/tint/lang/wgsl/reader/reader.h"
#include "src/tint/lang/wgsl/writer/writer.h"
#include "src/tint/lang/msl/writer/writer.h"
#include "src/tint/lang/hlsl/writer/writer.h"

namespace stc {

    ShaderSource ShaderCompiler::compile(
        const ShaderSource &source,
        ShaderSourceType fromType,
        ShaderSourceType toType
    ) {
        ShaderSource newSource;
        if (fromType == SHADER_SOURCE_TYPE_GLSL && toType == SHADER_SOURCE_TYPE_SPIRV) {
            newSource.type = SHADER_SOURCE_TYPE_SPIRV;
            newSource.binary = glslToSPIRV(source.text, source.stage);
        }
        else if (fromType == SHADER_SOURCE_TYPE_SPIRV && toType == SHADER_SOURCE_TYPE_WGSL) {
            newSource.type = SHADER_SOURCE_TYPE_WGSL;
            newSource.text = spirvToWGSL(source.binary);
        }
        else if (fromType == SHADER_SOURCE_TYPE_SPIRV && toType == SHADER_SOURCE_TYPE_MSL) {
            newSource.type = SHADER_SOURCE_TYPE_MSL;
            newSource.text = spirvToMSL(source.binary);
        }
        else if (fromType == SHADER_SOURCE_TYPE_SPIRV && toType == SHADER_SOURCE_TYPE_HLSL) {
            newSource.type = SHADER_SOURCE_TYPE_HLSL;
            newSource.text = spirvToHLSL(source.binary);
        }
    }

    std::vector<u32> ShaderCompiler::glslToSPIRV(const std::string &text, ShaderStage stage) {
        EShLanguage glslStage = {};

        switch (stage) {
            case SHADER_STAGE_VERTEX:
                glslStage = EShLangVertex;
                break;
            case SHADER_STAGE_FRAGMENT:
                glslStage = EShLangFragment;
                break;
            case SHADER_STAGE_COMPUTE:
                glslStage = EShLangCompute;
                break;
        }

        glslang::InitializeProcess();

        const char* strings[1] = { text.c_str() };
        glslang::TShader shader(glslStage);
        shader.setStrings(strings, 1);
        shader.setEnvInput(glslang::EShSourceGlsl, glslStage, glslang::EShClientVulkan, 100);
        shader.setEnvClient(glslang::EShClientVulkan, glslang::EShTargetVulkan_1_3);
        shader.setEnvTarget(glslang::EShTargetSpv, glslang::EShTargetSpv_1_6);

        if (!shader.parse(GetDefaultResources(), 100, false, EShMsgDefault)) {
            throw std::runtime_error(shader.getInfoLog());
        }

        glslang::TProgram program;
        program.addShader(&shader);
        program.link(EShMsgDefault);

        std::vector<uint32_t> spirv;
        glslang::GlslangToSpv(*program.getIntermediate(glslStage), spirv);
        glslang::FinalizeProcess();
        return spirv;
    }

    std::string ShaderCompiler::spirvToWGSL(const std::vector<u32>& spirv) {
        auto program = tint::spirv::reader::Read(spirv);
        if (!program.IsValid()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V to program!");
            return "";
        }

        auto wgsl = tint::wgsl::writer::Generate(program, {});
        if (wgsl->wgsl.empty()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V program to WGSL!");
            return "";
        }

        return wgsl->wgsl;
    }

    std::string ShaderCompiler::spirvToMSL(const std::vector<u32>& spirv) {
        auto program = tint::spirv::reader::Read(spirv);
        if (!program.IsValid()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V to program!");
            return "";
        }

        auto msl = tint::msl::writer::Generate(program, {});
        if (msl->msl.empty()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V program to MSL!");
            return "";
        }

        return msl->msl;
    }

    std::string ShaderCompiler::spirvToHLSL(const std::vector<u32> &spirv) {
        auto program = tint::spirv::reader::Read(spirv);
        if (!program.IsValid()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V to program!");
            return "";
        }

        auto hlsl = tint::hlsl::writer::Generate(program, {});
        if (hlsl->hlsl.empty()) {
            LOG_ERROR(TAG, "Failed to compile SPIR-V program to HLSL!");
            return "";
        }

        return hlsl->hlsl;
    }

}
