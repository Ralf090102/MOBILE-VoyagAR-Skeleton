cmake_minimum_required(VERSION 3.22.1)
cmake_policy(SET CMP0057 NEW)
project(voyagAR)

# Set the path to SDL sources
set(SDL_PATH ${CMAKE_CURRENT_SOURCE_DIR}/../../libs/SDL)

# Check if the SDL directory exists
if(NOT EXISTS ${SDL_PATH})
    message(FATAL_ERROR "SDL directory not found at ${SDL_PATH}")
endif()

# Include your project headers
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

# Include SDL headers and build config
include_directories(${SDL_PATH}/include)
include_directories(${SDL_PATH}/include/build_config)  # Include the directory with SDL_build_config_android.h

# Define the SDL build config location - point to the existing file
add_definitions(-DSDL_BUILD_CONFIG_H="${SDL_PATH}/include/build_config/SDL_build_config_android.h")
# Disable HIDAPI for Android
add_definitions(-DSDL_HIDAPI_DISABLED=1)
# Add SDL source files
# Base SDL sources (common across platforms)
file(GLOB SDL_CORE_SOURCES
        ${SDL_PATH}/src/atomic/*.c
        ${SDL_PATH}/src/audio/*.c
        ${SDL_PATH}/src/camera/*.c
        ${SDL_PATH}/src/cpuinfo/*.c
        ${SDL_PATH}/src/dynapi/*.c
        ${SDL_PATH}/src/events/*.c
        ${SDL_PATH}/src/file/*.c
        ${SDL_PATH}/src/io/*.c
        ${SDL_PATH}/src/joystick/*.c
        ${SDL_PATH}/src/math/*.c
        ${SDL_PATH}/src/misc/*.c
        ${SDL_PATH}/src/power/*.c
        ${SDL_PATH}/src/render/*.c
        ${SDL_PATH}/src/sensor/*.c
        ${SDL_PATH}/src/stdlib/*.c
        ${SDL_PATH}/src/thread/*.c
        ${SDL_PATH}/src/time/*.c
        ${SDL_PATH}/src/timer/*.c
        ${SDL_PATH}/src/video/*.c
        # Add other core directories as needed
)

# Android-specific sources
file(GLOB SDL_ANDROID_SOURCES
        ${SDL_PATH}/src/audio/android/*.c
        ${SDL_PATH}/src/core/android/*.c
        ${SDL_PATH}/src/filesystem/android/*.c
        ${SDL_PATH}/src/haptic/android/*.c
        ${SDL_PATH}/src/joystick/android/*.c
        ${SDL_PATH}/src/locale/android/*.c
        ${SDL_PATH}/src/power/android/*.c
        ${SDL_PATH}/src/sensor/android/*.c
        ${SDL_PATH}/src/video/android/*.c
        ${SDL_PATH}/src/thread/pthread/*.c
        ${SDL_PATH}/src/timer/unix/*.c
        ${SDL_PATH}/src/loadso/dlopen/*.c
)

# Combine the sources
set(SDL_SOURCES ${SDL_CORE_SOURCES} ${SDL_ANDROID_SOURCES})

# Make sure SDL can find its internal headers
include_directories(${SDL_PATH}/src)

# Create SDL library
add_library(SDL3 SHARED ${SDL_SOURCES})

# Add your native library
add_library(voyagAR SHARED
        src/main.cpp
        # Add more source files as needed
)

# Find required libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(GLESv1-lib GLESv1_CM)
find_library(GLESv2-lib GLESv2)
find_library(EGL-lib EGL)
find_library(dl-lib dl)
find_library(OpenSLES-lib OpenSLES)

# Link SDL against required libraries
target_link_libraries(SDL3
        ${log-lib}
        ${android-lib}
        ${GLESv1-lib}
        ${GLESv2-lib}
        ${EGL-lib}
        ${OpenSLES-lib}
        ${dl-lib}
)

# Link your app against SDL and other libraries
target_link_libraries(voyagAR
        SDL3
        ${log-lib}
        ${android-lib}
)

# Set C++ standard to C++17
set_target_properties(voyagAR PROPERTIES
        CXX_STANDARD 17
        CXX_STANDARD_REQUIRED YES
        CXX_EXTENSIONS NO
)