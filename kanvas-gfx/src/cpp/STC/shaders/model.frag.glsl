#version 450

layout(location = 0) in vec3 wPos;
layout(location = 1) in vec2 UV;
layout(location = 2) in vec3 wNormal;

layout(location = 0) out vec4 outColor;

layout(set = 0, binding = 0) uniform texture2D textures[1000];
layout(set = 0, binding = 1) uniform sampler materialSampler;

struct Material {
    // albedo mapping
    vec4 albedo;
    bool enableAlbedoMap;
    // normal mapping
    bool enableNormalMap;
    // metal mapping
    float metalness;
    bool enableMetalMap;
    // roughness mapping
    float roughness;
    bool enableRoughnessMap;
    // ambient occlusion mapping
    float ao;
    bool enableAOMap;
    // emission mapping
    vec3 emission;
    bool enableEmissionMap;
};

layout(set = 0, binding = 2) buffer MaterialBuffer {
    Material materials[];
};

void main() {
    Material material = materials[0];
    vec4 albedo = material.albedo * texture(sampler2D(textures[0], materialSampler), UV);
    outColor = albedo;
}