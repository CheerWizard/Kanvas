cmake_minimum_required(VERSION 3.22.1)
project(STC)

# print project info
message("CMAKE_BUILD_TYPE=${CMAKE_BUILD_TYPE}")
message("CMAKE_SYSTEM_NAME=${CMAKE_SYSTEM_NAME}")
message("CMAKE_SYSTEM_PROCESSOR=${CMAKE_SYSTEM_PROCESSOR}")

# configurations
add_compile_definitions($<$<CONFIG:Debug>:DEBUG>)

set(CMAKE_CXX_STANDARD 20)

if (NOT CMAKE_SYSTEM_NAME STREQUAL "Android")
    if(NOT DEFINED ENV{JAVA_HOME})
        message(FATAL_ERROR "Please set JAVA_HOME to your JDK")
    endif()

    set(JAVA_INCLUDE_DIR "$ENV{JAVA_HOME}/include")
    if(CMAKE_SYSTEM_NAME STREQUAL "Linux")
        set(PLATFORM_DIR "linux")
    elseif(CMAKE_SYSTEM_NAME STREQUAL "Darwin")
        set(PLATFORM_DIR "darwin")
    elseif(CMAKE_SYSTEM_NAME STREQUAL "Windows")
        set(PLATFORM_DIR "win32")
    elseif(CMAKE_SYSTEM_NAME STREQUAL "Emscripten" OR EMSCRIPTEN)
        # no-op
    else()
        message(FATAL_ERROR "Unsupported platform: ${CMAKE_SYSTEM_NAME}")
    endif()
    set(JAVA_INCLUDE_DIR_PLATFORM "$ENV{JAVA_HOME}/include/${PLATFORM_DIR}")

    include_directories(${JAVA_INCLUDE_DIR} ${JAVA_INCLUDE_DIR_PLATFORM})
endif()

if (CMAKE_SYSTEM_NAME STREQUAL "Windows")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../../desktopMain/resources/jni/windows-x86_64)
elseif (CMAKE_SYSTEM_NAME STREQUAL "Linux")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../../desktopMain/resources/jni/linux-x86_64)
elseif (CMAKE_SYSTEM_NAME STREQUAL "Darwin")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../../desktopMain/resources/jni/macos-x86_64)
endif()

include_directories(.)

# include all sources and exclude platform specific sources
file(GLOB_RECURSE SRC RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "*.cpp")
foreach(file ${SRC})
    if(file MATCHES "^bridges/jni/"
            OR file MATCHES "^bridges/cinterop/"
            OR file MATCHES "^bridges/wasm/"
            OR file MATCHES "^backend/vk/"
            OR file MATCHES "^backend/metal/"
            OR file MATCHES "^backend/webgpu/"
    )
        list(REMOVE_ITEM SRC ${file})
    endif()
endforeach()

include_directories(../external)

add_library(stb INTERFACE)
target_include_directories(stb INTERFACE ${stb_SOURCE_DIR})

macro(build_windows)
    file(GLOB_RECURSE PLATFORM_SRC bridges/jni/*.cpp backend/vk/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})
    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)
    target_link_libraries(${PROJECT_NAME}
            vulkan-1
            stbi
    )
endmacro()

macro(build_linux)
    file(GLOB_RECURSE PLATFORM_SRC bridges/jni/*.cpp backend/vk/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})
    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)
    target_link_libraries(${PROJECT_NAME}
            vulkan
            stbi
    )
endmacro()

macro(build_macos)
    file(GLOB_RECURSE PLATFORM_SRC bridges/cinterop/*.cpp backend/metal/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})
    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)
    target_link_libraries(${PROJECT_NAME}
            metal
            stbi
    )
endmacro()

macro(build_android)
    add_definitions(-DVK_USE_PLATFORM_ANDROID_KHR=1)
    file(GLOB_RECURSE PLATFORM_SRC bridges/jni/*.cpp backend/vk/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})
    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)
    target_link_options(${PROJECT_NAME} PRIVATE "-Wl,-z,max-page-size=16384")
    target_link_options(${PROJECT_NAME} PRIVATE "-Wl,-z,common-page-size=16384")
    target_link_libraries(${PROJECT_NAME}
            vulkan
            android
            stbi
    )
endmacro()

macro(build_ios)
    file(GLOB_RECURSE PLATFORM_SRC bridges/cinterop/*.cpp backend/metal/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})
    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)
    target_link_libraries(${PROJECT_NAME}
            metal
            stbi
    )
endmacro()

macro(build_web)
    file(GLOB_RECURSE PLATFORM_SRC bridges/wasm/*.cpp backend/webgpu/*.cpp)
    add_library(${PROJECT_NAME} SHARED ${SRC} ${PLATFORM_SRC})

    if (DEFINED EMSCRIPTEN)
        message(STATUS "Detected Emscripten SDK at: ${EMSCRIPTEN_ROOT_PATH}")
    endif()

    target_include_directories(${PROJECT_NAME} PRIVATE
            ${EMSCRIPTEN_ROOT_PATH}/system/include
            ${EMSCRIPTEN_ROOT_PATH}/system/include/libc
            ${EMSCRIPTEN_ROOT_PATH}/system/include/libcxx
            ${EMSCRIPTEN_ROOT_PATH}/system/include/libcxxabi
            ${EMSCRIPTEN_ROOT_PATH}/system/include/webgpu
    )

    target_precompile_headers(${PROJECT_NAME} PRIVATE pch.hpp)

    target_compile_options(${PROJECT_NAME} PRIVATE
            "-sUSE_WEBGPU=1"
            "-sALLOW_MEMORY_GROWTH=1"
            "-fexceptions"
    )

    target_link_options(${PROJECT_NAME} PRIVATE
            "-sUSE_WEBGPU=1"
            "-sALLOW_MEMORY_GROWTH=1"
            "-sEXPORTED_FUNCTIONS=['_main']"
            "-sEXPORTED_RUNTIME_METHODS=['ccall','cwrap']"
            "-sFORCE_FILESYSTEM=1"
            "-sMODULARIZE=1"
            "-sENVIRONMENT=web"
            "-sASYNCIFY=1"
            "-sASYNCIFY_IMPORTS=['wgpuCreateAsync', 'loadFileAsync']"
            $<$<CONFIG:Debug>:-sASYNCIFY_DEBUG=1>
            "--bind"
    )

    target_link_libraries(${PROJECT_NAME} PRIVATE stbi)
endmacro()

# defines for OS
if (CMAKE_SYSTEM_NAME STREQUAL "Windows")
    find_package(Vulkan REQUIRED)
    add_definitions(-DWINDOWS -DDESKTOP -DVK)
    build_windows()
elseif (CMAKE_SYSTEM_NAME STREQUAL "Linux")
    find_package(Vulkan REQUIRED)
    add_definitions(-DLINUX -DDESKTOP -DVK)
    build_linux()
elseif (CMAKE_SYSTEM_NAME STREQUAL "Darwin")
    add_definitions(-DMACOS -DDESKTOP -DMETAL)
    build_macos()
elseif (CMAKE_SYSTEM_NAME STREQUAL "Android")
    find_package(Vulkan REQUIRED)
    add_definitions(-DANDROID -DMOBILE -DVK)
    build_android()
elseif (CMAKE_SYSTEM_NAME STREQUAL "iOS")
    add_definitions(-DIOS -DMOBILE -DMETAL)
    build_ios()
elseif (CMAKE_SYSTEM_NAME STREQUAL "Emscripten" OR EMSCRIPTEN)
    add_definitions(-DWEB -DWEBGPU)
    build_web()
endif ()

# optionally enable warnings
#target_compile_options(${PROJECT_NAME} PRIVATE -Wall -Werror)