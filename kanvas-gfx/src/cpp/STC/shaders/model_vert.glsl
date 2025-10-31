#version 450

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec2 aUV;
layout(location = 2) in vec3 aNormal;

layout(location = 0) out vec3 wPos;
layout(location = 1) out vec2 UV;
layout(location = 2) out vec3 wNormal;

layout(set = 0, binding = 0) uniform CameraData {
    mat4 projection;
    mat4 view;
} camera;

layout(set = 0, binding = 1) uniform TransformData {
    mat4 model;
    mat3 normal;
} transform;

void main() {
    UV = aUV;
    wPos = (transform.model * vec4(aPos, 1.0)).xyz;
    wNormal = transform.normal * aNormal;
    gl_Position = camera.projection * camera.view * transform.model * vec4(aPos, 1.0);
}