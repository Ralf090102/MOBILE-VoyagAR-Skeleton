cmake_minimum_required(VERSION 3.22.1)

project(Anito-VoyagAR)

if (ANDROID)
    message(STATUS "Building for Android!")
    add_definitions(-DANDROID -D__ANDROID__)
else()
    message(FATAL_ERROR "This project is only for Android! CMake is targeting a non-Android platform.")
endif()

set(ANDROID_PLATFORM 27)
set(ANDROID_STL c++_static)
set(ANDROID_ABI armeabi-v7a arm64-v8a x86 x86_64)

set(CMAKE_DISABLE_SOURCE_CHANGES ON)
set(CMAKE_DISABLE_IN_SOURCE_BUILDS ON)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_BUILD_TYPE_INIT "Debug")

add_subdirectory(SDL)
add_subdirectory(VoyagAR)

