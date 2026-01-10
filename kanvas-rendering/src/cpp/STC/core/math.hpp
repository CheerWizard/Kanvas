//
// Created by cheerwizard on 07.07.25.
//

#ifndef MATH_HPP
#define MATH_HPP

#include <algorithm>
#include <cmath>

namespace stc {

#define RADIANS(x) ((x) * 1_PI / 180.0f)

    constexpr float operator ""_PI(unsigned long long x) {
        return x * 3.14159265359f;
    }

    constexpr float operator ""_E(unsigned long long x) {
        return x * 2.71828f;
    }

    constexpr float operator ""_RAD(unsigned long long x) {
        return RADIANS(x);
    }

    inline float clamp(float a, float b, float x) {
        return std::min(std::max(x, b), a);
    }

    inline float lerp(float a, float b, float x) {
        return x * (b - a) + a;
    }

    inline float step(float a, float x) {
        return clamp(0, 1, x - a);
    }

    inline float smoothstep(float a, float b, float x) {
        float t = clamp(0, 1, (x - a) / (b - a));
        float y = -2 * t * t * t + 3 * t * t;
        return y;
    }

    template<typename T>
    struct vec2 {

        union {
            struct {
                T xy[2];
            };
            struct {
                T rg[2];
            };
            struct {
                T x, y;
            };
            struct {
                T r, g;
            };
        };

        vec2() = default;

        vec2(T x, T y) : x(x), y(y) {}

        vec2(const vec2<T>& v) = default;

        T& operator [](int i) {
            return *(&x + i);
        }

        const T& operator [](int i) const {
            return *(&x + i);
        }

        friend vec2 operator +(const vec2& v1, const vec2& v2) {
            return { v1.x + v2.x, v1.y + v2.y };
        }

        friend vec2 operator -(const vec2& v1, const vec2& v2) {
            return { v1.x - v2.x, v1.y - v2.y };
        }

        friend vec2 operator *(const vec2& v1, const vec2& v2) {
            return { v1.x * v2.x, v1.y * v2.y };
        }

        friend vec2 operator /(const vec2& v1, const vec2& v2) {
            return { v1.x / v2.x, v1.y / v2.y };
        }

        friend vec2 operator +(const vec2& v1, const T& s) {
            return { v1.x + s, v1.y + s };
        }

        friend vec2 operator -(const vec2& v1, const T& s) {
            return { v1.x - s, v1.y - s };
        }

        friend vec2 operator *(const vec2& v1, const T& s) {
            return { v1.x * s, v1.y * s };
        }

        friend vec2 operator /(const vec2& v1, const T& s) {
            return { v1.x / s, v1.y / s };
        }

        friend vec2 operator +(const T& s, const vec2& v2) {
            return { s + v2.x, s + v2.y };
        }

        friend vec2 operator -(const T& s, const vec2& v2) {
            return { s - v2.x, s - v2.y };
        }

        friend vec2 operator *(const T& s, const vec2& v2) {
            return { s * v2.x, s * v2.y };
        }

        friend vec2 operator /(const T& s, const vec2& v2) {
            return { s / v2.x, s / v2.y };
        }

        friend vec2 operator ^(const vec2& v, const T& p) {
            return { Pow(v.x, p), Pow(v.y, p) };
        }

        friend vec2 operator -(const vec2& v) {
            return { -v.x, -v.y };
        }
    };

    template<typename T>
    T length(const vec2<T>& v) {
        return std::sqrt(v.x * v.x + v.y * v.y);
    }

    template<typename T>
    vec2<T> normalize(const vec2<T>& v) {
        T l = length(v);
        return { v.x / l, v.y / l };
    }

    template<typename T>
    T dot(const vec2<T>& v1, const vec2<T>& v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    template<typename T>
    T cross(const vec2<T>& v1, const vec2<T>& v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }

    template<typename T>
    struct vec3 {

        union {
            struct {
                T xyz[3];
            };
            struct {
                T rgb[3];
            };
            struct {
                T x, y, z;
            };
            struct {
                T r, g, b;
            };
        };

        vec3() = default;

        vec3(T x, T y, T z) : x(x), y(y), z(z) {}

        vec3(const vec2<T>& v) : x(v.x), y(v.y) {}

        vec3(const vec3<T>& v) = default;

        T& operator [](int i) {
            return *(&x + i);
        }

        const T& operator [](int i) const {
            return *(&x + i);
        }

        friend vec3 operator +(const vec3& v1, const vec3& v2) {
            return { v1.x + v2.x, v1.y + v2.y, v1.z + v2.z };
        }

        friend vec3 operator -(const vec3& v1, const vec3& v2) {
            return { v1.x - v2.x, v1.y - v2.y, v1.z - v2.z };
        }

        friend vec3 operator *(const vec3& v1, const vec3& v2) {
            return { v1.x * v2.x, v1.y * v2.y, v1.z * v2.z };
        }

        friend vec3 operator /(const vec3& v1, const vec3& v2) {
            return { v1.x / v2.x, v1.y / v2.y, v1.z / v2.z };
        }

        friend vec3 operator +(const vec3& v1, const T& s) {
            return { v1.x + s, v1.y + s, v1.z + s };
        }

        friend vec3 operator -(const vec3& v1, const T& s) {
            return { v1.x - s, v1.y - s, v1.z - s };
        }

        friend vec3 operator *(const vec3& v1, const T& s) {
            return { v1.x * s, v1.y * s, v1.z * s };
        }

        friend vec3 operator /(const vec3& v1, const T& s) {
            return { v1.x / s, v1.y / s, v1.z / s };
        }

        friend vec3 operator +(const T& s, const vec3& v2) {
            return { s + v2.x, s + v2.y, s + v2.z };
        }

        friend vec3 operator -(const T& s, const vec3& v2) {
            return { s - v2.x, s - v2.y, s - v2.z };
        }

        friend vec3 operator *(const T& s, const vec3& v2) {
            return { s * v2.x, s * v2.y, s * v2.z };
        }

        friend vec3 operator /(const T& s, const vec3& v2) {
            return { s / v2.x, s / v2.y, s / v2.z };
        }

        friend vec3 operator ^(const vec3& v, const T& p) {
            return { Pow(v.x, p), Pow(v.y, p), Pow(v.z, p) };
        }

        friend vec3 operator -(const vec3& v) {
            return { -v.x, -v.y, -v.z };
        }

        vec2<T> xy() const {
            return { x, y };
        }
    };

    template<typename T>
    T length(const vec3<T>& v) {
        return std::sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }

    template<typename T>
    vec3<T> normalize(const vec3<T>& v) {
        T l = length(v);
        return { v.x / l, v.y / l, v.z / l };
    }

    template<typename T>
    T dot(const vec3<T>& v1, const vec3<T>& v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    template<typename T>
    vec3<T> cross(const vec3<T>& v1, const vec3<T>& v2) {
        return {
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x,
        };
    }

    template<typename T>
    struct vec4 {
        union {
            struct {
                T xyzw[4];
            };
            struct {
                T rgba[4];
            };
            struct {
                T x, y, z, w;
            };
            struct {
                T r, g, b, a;
            };
        };

        vec4() = default;

        vec4(T x, T y, T z, T w) : x(x), y(y), z(z), w(w) {}

        vec4(const vec2<T>& v) : x(v.x), y(v.y) {}

        vec4(const vec3<T>& v) : x(v.x), y(v.y), z(v.z) {}

        vec4(const vec4<T>& v) = default;

        T& operator [](int i) {
            return *(&x + i);
        }

        const T& operator [](int i) const {
            return *(&x + i);
        }

        friend vec4 operator +(const vec4& v1, const vec4& v2) {
            return { v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w };
        }

        friend vec4 operator -(const vec4 v1, const vec4& v2) {
            return { v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w };
        }

        friend vec4 operator *(const vec4& v1, const vec4& v2) {
            return { v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w };
        }

        friend vec4 operator /(const vec4& v1, const vec4& v2) {
            return { v1.x / v2.x, v1.y / v2.y, v1.z / v2.z, v1.w / v2.w };
        }

        friend vec4 operator +(const vec4& v1, const T& s) {
            return { v1.x + s, v1.y + s, v1.z + s, v1.w + s };
        }

        friend vec4 operator -(const vec4& v1, const T& s) {
            return { v1.x - s, v1.y - s, v1.z - s, v1.w - s };
        }

        friend vec4 operator *(const vec4& v1, const T& s) {
            return { v1.x * s, v1.y * s, v1.z * s, v1.w * s };
        }

        friend vec4 operator /(const vec4& v1, const T& s) {
            return { v1.x / s, v1.y / s, v1.z / s, v1.w / s };
        }

        friend vec4 operator +(const T& s, const vec4& v2) {
            return { s + v2.x, s + v2.y, s + v2.z, s + v2.w };
        }

        friend vec4 operator -(const T& s, const vec4& v2) {
            return { s - v2.x, s - v2.y, s - v2.z, s - v2.w };
        }

        friend vec4 operator *(const T& s, const vec4& v2) {
            return { s * v2.x, s * v2.y, s * v2.z, s * v2.w };
        }

        friend vec4 operator /(const T& s, const vec4& v2) {
            return { s / v2.x, s / v2.y, s / v2.z, s / v2.w };
        }

        friend vec4 operator ^(const vec4& v, const T& p) {
            return { Pow(v.x, p), Pow(v.y, p), Pow(v.z, p), Pow(v.w, p) };
        }

        friend vec4 operator -(const vec4& v) {
            return { -v.x, -v.y, -v.z, -v.w };
        }

        vec2<T> xy() const {
            return { x, y };
        }

        vec3<T> xyz() const {
            return { x, y, z };
        }
    };

    template<typename T>
    T length(const vec4<T>& v) {
        return std::sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
    }

    template<typename T>
    vec4<T> normalize(const vec4<T>& v) {
        T l = length(v);
        return { v.x / l, v.y / l, v.z / l, v.w / l };
    }

    template<typename T>
    T dot(const vec4<T>& v1, const vec4<T>& v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
    }

    template<typename T>
    vec4<T> cross(const vec4<T>& v1, const vec4<T>& v2) {
        // TODO: not implemented!
        return {};
    }

    struct quaternion final {
        float x = 0;
        float y = 0;
        float z = 0;
        float w = 1;

        quaternion() = default;

        quaternion(float x, float y, float z, float w) : x(x), y(y), z(z), w(w) {}

        quaternion(const vec3<float>& n, float r = 0) {
            x = n.x * std::sin(r * 0.5f);
            y = n.y * std::sin(r * 0.5f);
            z = n.z * std::sin(r * 0.5f);
            w = std::cos(r * 0.5f);
        }

        friend quaternion operator *(const quaternion& q1, const quaternion& q2) {
            quaternion q3;
            q3.x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y;
            q3.y = q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x;
            q3.z = q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w;
            q3.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
            return q3;
        }

        friend quaternion operator-(const quaternion& q) {
            return { -q.x, -q.y, -q.z, q.w };
        }

        vec2<float> xy() const {
            return { x, y };
        }

        vec3<float> xyz() const {
            return { x, y, z };
        }
    };

    inline float length(const quaternion& q) {
        return std::sqrt(q.x * q.x + q.y * q.y + q.z * q.z);
    }

    inline quaternion normalize(const quaternion& q) {
        float l = length(q);
        return { q.x / l, q.y / l, q.z / l, q.w };
    }

    inline quaternion rotate(const quaternion& q, const vec3<float>& n) {
        return q * quaternion(n) * -q;
    }

    inline quaternion slerp(const quaternion& q1, const quaternion& q2, float t) {
        quaternion q3;
        float dot = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
        float theta, st, sut, sout, coeff1, coeff2;

        t /= 2.0;

        theta = std::acos(dot);
        if (theta < 0.0) {
            theta=-theta;
        }

        st = std::sin(theta);
        sut = std::sin(t*theta);
        sout = std::sin((1-t)*theta);
        coeff1 = sout/st;
        coeff2 = sut/st;

        q3.x = coeff1*q1.x + coeff2*q2.x;
        q3.y = coeff1*q1.y + coeff2*q2.y;
        q3.z = coeff1*q1.z + coeff2*q2.z;
        q3.w = coeff1*q1.w + coeff2*q2.w;

        q3 = normalize(q3);

        return q3;
    }

    template<typename T>
    struct mat2 {
        vec2<T> m[2];

        mat2() = default;

        mat2(
                T m00, T m01,
                T m10, T m11
        ) {
            m[0][0] = m00;
            m[0][1] = m01;

            m[1][0] = m10;
            m[1][1] = m11;
        }

        mat2(const vec2<T>& v0, const vec2<T>& v1) {
            m[0] = v0;
            m[1] = v1;
        }

        vec2<T>& operator [](int i) {
            return m[i];
        }

        const vec2<T>& operator [](int i) const {
            return m[i];
        }

        friend mat2 operator *(const mat2& m1, const mat2& m2) {
            mat2 m3;
            for (int r = 0 ; r < 2 ; r++) {
                for (int c = 0 ; c < 2 ; c++) {
                    for (int i = 0 ; i < 2 ; i++) {
                        m3[r][c] += m1[r][i] * m2[i][c];
                    }
                }
            }
            return m3;
        }

        mat2 operator -() const {
            mat2 n;
            for (int r = 0 ; r < 2 ; r++) {
                for (int c = 0 ; c < 2 ; c++) {
                    n[r][c] = -m[r][c];
                }
            }
            return n;
        }
    };

    template<typename T>
    mat2<T> transpose(const mat2<T>& m) {
        mat2<T> t = m;

        // Swap(t[0][0], t[0][0]);
        std::swap(t[0][1], t[1][0]);

        // Swap(t[1][0], t[0][1]);
        // Swap(t[1][1], t[1][1]);

        return t;
    }

    template<typename T>
    T det(const mat2<T>& m) {
        return m[0][0] * m[1][1] - m[1][0] * m[0][1];
    }

    template<typename T>
    mat2<T> inverse(const mat2<T>& m) {
        mat2<T> c;
        T d = det(m);
        c[0][0] = m[1][1];
        c[0][1] =-m[1][0];
        c[1][0] =-m[0][1];
        c[1][1] = m[0][0];
        c /= d;
        return c;
    }

    template<typename T>
    struct mat3 {
        vec3<T> m[3];

        mat3() = default;

        mat3(
                T m00, T m01, T m02,
                T m10, T m11, T m12,
                T m20, T m21, T m22
        ) {
            m[0][0] = m00;
            m[0][1] = m01;
            m[0][2] = m02;

            m[1][0] = m10;
            m[1][1] = m11;
            m[1][2] = m12;

            m[2][0] = m20;
            m[2][1] = m21;
            m[2][2] = m22;
        }

        mat3(const vec3<T>& v0, const vec3<T>& v1, const vec3<T>& v2) {
            m[0] = v0;
            m[1] = v1;
            m[2] = v2;
        }

        vec3<T>& operator [](int i) {
            return m[i];
        }

        const vec3<T>& operator [](int i) const {
            return m[i];
        }

        friend mat3 operator *(const mat3& m1, const mat3& m2) {
            mat3 m3;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    for (int i = 0; i < 3; i++) {
                        m3[r][c] += m1[r][i] * m2[i][c];
                    }
                }
            }
            return m3;
        }

        mat3 operator -() const {
            mat3 n;
            for (int r = 0 ; r < 3 ; r++) {
                for (int c = 0 ; c < 3 ; c++) {
                    n[r][c] = -m[r][c];
                }
            }
            return n;
        }
    };

    template<typename T>
    mat3<T> transpose(const mat3<T>& m) {
        mat3<T> t = m;

        // Swap(t[0][0], t[0][0]);
        std::swap(t[0][1], t[1][0]);
        std::swap(t[0][2], t[2][0]);

        // Swap(t[1][0], t[0][1]);
        // Swap(t[1][1], t[1][1]);
        std::swap(t[1][2], t[2][1]);

        // Swap(t[2][0], t[0][2]);
        // Swap(t[2][1], t[1][2]);
        // Swap(t[2][2], t[2][2]);

        return t;
    }

    template<typename T>
    T det(const mat3<T>& m) {
        T d = m[0][0] * m[1][1] * m[2][2]
              + m[0][1] * m[1][2] * m[2][0]
              + m[0][2] * m[1][0] * m[2][1]
              - m[2][0] * m[1][1] * m[0][2]
              - m[2][1] * m[1][2] * m[0][0]
              - m[2][2] * m[1][0] * m[0][1];
        return d;
    }

    template<typename T>
    mat3<T> inverse(const mat3<T>& m) {
        T d = det(m);

        mat3<T> c;

        c[0][0] = det(mat2<T> {
                m[1][1], m[1][2],
                m[2][1], m[2][2]
        });

        c[0][1] = -det(mat2<T> {
                m[1][0], m[1][2],
                m[2][0], m[2][2]
        });

        c[0][2] = det(mat2<T> {
                m[1][0], m[1][1],
                m[2][0], m[2][1]
        });

        c[1][0] = -det(mat2<T> {
                m[0][0], m[0][1],
                m[2][0], m[2][1]
        });

        c[1][1] = det(mat2<T> {
                m[0][0], m[0][2],
                m[2][0], m[2][2]
        });

        c[1][2] = -det(mat2<T> {
                m[0][0], m[0][1],
                m[2][0], m[2][1]
        });

        c[2][0] = det(mat2<T> {
                m[0][1], m[0][2],
                m[1][1], m[1][2]
        });

        c[2][1] = -det(mat2<T> {
                m[0][0], m[0][2],
                m[1][0], m[1][2]
        });

        c[2][2] = det(mat2<T> {
                m[0][0], m[0][1],
                m[1][0], m[1][1]
        });

        c /= d;

        return c;
    }

    template<typename T>
    struct mat4 {
        vec4<T> m[4];

        mat4() = default;

        mat4(T value) {
            for (int x = 0 ; x < 4 ; x++) {
                for (int y = 0 ; y < 4 ; y++) {
                    m[x][y] = value;
                }
            }
        }

        mat4(
                T m00, T m01, T m02, T m03,
                T m10, T m11, T m12, T m13,
                T m20, T m21, T m22, T m23,
                T m30, T m31, T m32, T m33
        ) {
            m[0][0] = m00;
            m[0][1] = m01;
            m[0][2] = m02;
            m[0][3] = m03;

            m[1][0] = m10;
            m[1][1] = m11;
            m[1][2] = m12;
            m[1][3] = m13;

            m[2][0] = m20;
            m[2][1] = m21;
            m[2][2] = m22;
            m[2][3] = m23;

            m[3][0] = m30;
            m[3][1] = m31;
            m[3][2] = m32;
            m[3][3] = m33;
        }

        mat4(const vec4<T> &v0, const vec4<T> &v1, const vec4<T> &v2, const vec4<T> &v3) {
            m[0] = v0;
            m[1] = v1;
            m[2] = v2;
            m[3] = v3;
        }

        mat4(const vec3<T> &v0, const vec3<T> &v1, const vec3<T> &v2, const vec3<T> &v3) {
            m[0] = v0;
            m[1] = v1;
            m[2] = v2;
            m[3] = v3;
        }

        mat4(const quaternion &q) {
            T xx = q.x * q.x;
            T xy = q.x * q.y;
            T xz = q.x * q.z;
            T yy = q.y * q.y;
            T zz = q.z * q.z;
            T yz = q.y * q.z;
            T wx = q.w * q.x;
            T wy = q.w * q.y;
            T wz = q.w * q.z;

            m[0][0] = 1.0f - 2.0f * (yy + zz);
            m[1][0] = 2.0f * (xy - wz);
            m[2][0] = 2.0f * (xz + wy);
            m[3][0] = 0.0;

            m[0][1] = 2.0f * (xy + wz);
            m[1][1] = 1.0f - 2.0f * (xx + zz);
            m[2][1] = 2.0f * (yz - wx);
            m[3][1] = 0.0;

            m[0][2] = 2.0f * (xz - wy);
            m[1][2] = 2.0f * (yz + wx);
            m[2][2] = 1.0f - 2.0f * (xx + yy);
            m[3][2] = 0.0;

            m[0][3] = 0;
            m[1][3] = 0;
            m[2][3] = 0;
            m[3][3] = 1;
        }

        vec4<T>& operator[](int i) {
            return m[i];
        }

        const vec4<T>& operator[](int i) const {
            return m[i];
        }

        friend mat4 operator*(const mat4 &m1, const mat4 &m2) {
            mat4 m3;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    for (int i = 0; i < 4; i++) {
                        m3[r][c] += m1[r][i] * m2[i][c];
                    }
                }
            }
            return m3;
        }

        mat4 operator-() const {
            mat4 n;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    n[r][c] = -m[r][c];
                }
            }
            return n;
        }

        friend mat4 operator/(const mat4& m1, T v) {
            mat4 m2 = m1;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    m2[r][c] /= v;
                }
            }
            return m2;
        }

        friend mat4 operator/=(const mat4& m1, T v) {
            mat4 m2 = m1;
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    m2[r][c] /= v;
                }
            }
            return m2;
        }
    };

    template<typename T>
    mat4<T> transpose(const mat4<T>& m) {
        mat4<T> t = m;

        // Swap(t[0][0], t[0][0]);
        std::swap(t[0][1], t[1][0]);
        std::swap(t[0][2], t[2][0]);
        std::swap(t[0][3], t[3][0]);

        // Swap(t[1][0], t[0][1]);
        // Swap(t[1][1], t[1][1]);
        std::swap(t[1][2], t[2][1]);
        std::swap(t[1][3], t[3][1]);

        // Swap(t[2][0], t[0][2]);
        // Swap(t[2][1], t[1][2]);
        // Swap(t[2][2], t[2][2]);
        std::swap(t[2][3], t[3][2]);

        // Swap(t[3][0], t[0][3]);
        // Swap(t[3][1], t[1][3]);
        // Swap(t[3][2], t[2][3]);
        // Swap(t[3][3], t[3][3]);

        return t;
    }

    template<typename T>
    T det(const mat4<T>& m) {
        T d = m[0][0] * m[1][1] * m[2][2] * m[3][3]
              + m[0][1] * m[1][2] * m[2][3] * m[3][0]
              + m[0][2] * m[1][3] * m[2][0] * m[3][1]
              - m[3][0] * m[2][1] * m[1][2] * m[0][3]
              - m[3][1] * m[2][2] * m[1][3] * m[0][0]
              - m[3][2] * m[2][3] * m[1][0] * m[0][1];
        return d;
    }

    template<typename T>
    mat4<T> inverse(const mat4<T>& m) {
        T d = det(m);

        mat4<T> c;

        c[0][0] = det(mat3<T>{
                m[1][1], m[1][2], m[1][3],
                m[2][1], m[2][2], m[2][3],
                m[3][1], m[3][2], m[3][3]
        });

        c[0][1] = -det(mat3<T>{
                m[1][0], m[1][2], m[1][3],
                m[2][0], m[2][2], m[2][3],
                m[3][0], m[3][2], m[3][3]
        });

        c[0][2] = det(mat3<T>{
                m[1][0], m[1][1], m[1][3],
                m[2][0], m[2][1], m[2][3],
                m[3][0], m[3][1], m[3][3]
        });

        c[0][3] = -det(mat3<T>{
                m[1][0], m[1][1], m[1][2],
                m[2][0], m[2][1], m[2][2],
                m[3][0], m[3][1], m[3][2]
        });

        c[1][0] = -det(mat3<T>{
                m[0][1], m[0][2], m[0][3],
                m[2][1], m[2][2], m[2][3],
                m[3][1], m[3][2], m[3][3]
        });

        c[1][1] = det(mat3<T>{
                m[0][0], m[0][2], m[0][3],
                m[2][0], m[2][2], m[2][3],
                m[3][0], m[3][2], m[3][3]
        });

        c[1][2] = -det(mat3<T>{
                m[0][0], m[0][1], m[0][3],
                m[2][0], m[2][1], m[2][3],
                m[3][0], m[3][1], m[3][3]
        });

        c[1][3] = det(mat3<T>{
                m[0][0], m[0][1], m[0][2],
                m[2][0], m[2][1], m[2][2],
                m[3][0], m[3][1], m[3][2]
        });

        c[2][0] = det(mat3<T>{
                m[0][1], m[0][2], m[0][3],
                m[1][1], m[1][2], m[1][3],
                m[3][1], m[3][2], m[3][3]
        });

        c[2][1] = -det(mat3<T>{
                m[0][0], m[0][2], m[0][3],
                m[1][0], m[1][2], m[1][3],
                m[3][0], m[3][2], m[3][3]
        });

        c[2][2] = det(mat3<T>{
                m[0][0], m[0][1], m[0][3],
                m[1][0], m[1][1], m[1][3],
                m[3][0], m[3][1], m[3][3]
        });

        c[2][3] = -det(mat3<T>{
                m[0][0], m[0][1], m[0][2],
                m[1][0], m[1][1], m[1][2],
                m[3][0], m[3][1], m[3][2]
        });

        c[3][0] = -det(mat3<T>{
                m[0][1], m[0][2], m[0][3],
                m[1][1], m[1][2], m[1][3],
                m[2][1], m[2][2], m[2][3]
        });

        c[3][1] = det(mat3<T>{
                m[0][0], m[0][2], m[0][3],
                m[1][0], m[1][2], m[1][3],
                m[2][0], m[2][2], m[2][3]
        });

        c[3][2] = -det(mat3<T>{
                m[0][0], m[0][1], m[0][3],
                m[1][0], m[1][1], m[1][3],
                m[2][0], m[2][1], m[2][3]
        });

        c[3][3] = det(mat3<T>{
                m[0][0], m[0][1], m[0][2],
                m[1][0], m[1][1], m[1][2],
                m[2][0], m[2][1], m[2][2]
        });

        c /= d;

        return c;
    }

    template<typename T>
    mat4<T> fastInverseMatrix(const mat4<T>& m) {
        // TODO(cheerwizard): not implemented!
        return {};
    }

    inline mat4<float> translate(const mat4<float>& m, const vec3<float>& translation) {
        mat4<float> n = m;
        n[0][3] += translation.x;
        n[1][3] += translation.y;
        n[2][3] += translation.z;
        return n;
    }

    inline mat4<float> scale(const mat4<float>& m, const vec3<float>& scalar) {
        mat4<float> n = m;
        n[0][0] *= scalar.x;
        n[1][1] *= scalar.y;
        n[2][2] *= scalar.z;
        return n;
    }

    inline mat4<float> rotate(const mat4<float>& m, const vec3<float>& r, const vec3<float>& axis) {
        mat4<float> rx;
        {
            float sinx = std::sin(r.x);
            float cosx = std::cos(r.x);
            rx[1][1] = cosx;
            rx[1][2] = -sinx;
            rx[2][1] = sinx;
            rx[2][2] = cosx;
            rx[0][0] = axis.x;
        }

        mat4<float> ry;
        {
            float siny = std::sin(r.y);
            float cosy = std::cos(r.y);
            ry[0][0] = cosy;
            ry[0][2] = siny;
            ry[2][0] = -siny;
            ry[2][2] = cosy;
            ry[1][1] = axis.y;
        }

        mat4<float> rz;
        {
            float sinz = std::sin(r.z);
            float cosz = std::cos(r.z);
            rz[0][0] = cosz;
            rz[0][1] = -sinz;
            rz[1][0] = sinz;
            rz[1][1] = cosz;
            rz[2][2] = axis.z;
        }

        return m * rz * ry * rx;
    }

    static mat4<float> model_matrix(const vec3<float>& translation, const quaternion& rotation, const vec3<float>& scalar) {
        mat4<float> m;
        translate(m, translation);
        m = m * mat4<float>(rotation);
        scale(m, scalar);
        return m;
    }

    static mat4<float> rigidbody_matrix(const vec3<float>& translation, const quaternion& rotation) {
        mat4<float> m;
        translate(m, translation);
        m = m * mat4<float>(rotation);
        return m;
    }

    static mat4<float> view_matrix(const vec3<float>& position, const vec3<float>& front, const vec3<float>& up) {
        vec3<float> right = normalize(cross(front, up));
        return fastInverseMatrix(transpose(mat4<float>(
                right,
                cross(right, front),
                -front,
                position
        )));
    }

    static mat4<float> ortho_matrix(float left, float right, float bottom, float top, float z_near, float z_far) {
        return mat4<float>(
                { 2.0f / (right - left), 0.0f, 0.0f, 0.0f },
                { 0.0f, 2.0f / (bottom - top), 0.0f, 0.0f },
                { 0.0f, 0.0f, 1.0f / (z_near - z_far), 0.0f },
                { -(right + left) / (right - left), -(bottom + top) / (bottom - top), z_near / (z_near - z_far), 1.0f }
        );
    }

    static mat4<float> perspective_matrix(float aspect, float fovDegree, float z_near, float z_far) {
        float f = 1.0f / tan(RADIANS(0.5f * fovDegree));
        return mat4<float> {
            { f / aspect, 0.0f, 0.0f, 0.0f },
            { 0.0f, -f, 0.0f, 0.0f },
            { 0.0f, 0.0f, z_far / (z_near - z_far), -1.0f },
            { 0.0f, 0.0f, z_near * z_far / (z_near - z_far), 0.0f }
        };
    }

    static mat4<float> normal_matrix(const mat4<float>& model) {
        return transpose(inverse(model));
    }

    using float2 = vec2<float>;
    using int2 = vec2<int>;
    using uint2 = vec2<u32>;

    using float3 = vec3<float>;
    using int3 = vec3<int>;
    using uint3 = vec3<u32>;

    using float4 = vec4<float>;
    using int4 = vec4<int>;
    using uint4 = vec4<u32>;

    using float2x2 = mat2<float>;
    using int2x2 = mat2<int>;
    using uint2x2 = mat2<u32>;

    using float3x3 = mat3<float>;
    using int3x3 = mat3<int>;
    using uint3x3 = mat3<u32>;

    using float4x4 = mat4<float>;
    using int4x4 = mat4<int>;
    using uint4x4 = mat4<u32>;

    struct ModelMat {
        float3 position = { 0, 0, 0 };
        float3 rotation = { 0, 0, 0 };
        float3 scale = { 1, 1, 1 };
    };

    struct RigidBodyMat {
        float3 position = { 0, 0, 0 };
        float3 rotation = { 0, 0, 0 };
    };

    struct ViewMat {
        float3 position = { 0, 0, 1 };
        float3 front = { 0, 0, 0 };
        float3 up = { 0, 1, 0 };
    };

    struct OrthoMat {
        float left = 0;
        float right = 1;
        float bottom = 0;
        float top = 1;
        float near = 0.1f;
        float far = 1.0f;
    };

    struct PerspectiveMat {
        float fovDegree = 60.0f;
        float aspectRatio = 1.3f;
        float near = 0.1f;
        float far = 100.0f;
    };

}

#endif //MATH_HPP
