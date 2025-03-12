cmake_minimum_required(VERSION 3.22.1)
cmake_policy(SET CMP0057 NEW)
project(voyagAR)

# Set the path to SDL sources - adjust this path to where SDL is located in your project
set(SDL_PATH ${CMAKE_CURRENT_SOURCE_DIR}/../../libs/SDL)

# Check if the SDL directory exists
if(NOT EXISTS ${SDL_PATH})
    message(FATAL_ERROR "SDL directory not found at ${SDL_PATH}")
endif()

# Include your project headers
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

# Include SDL headers
include_directories(${SDL_PATH}/include)

# Add your native library
add_library(voyagAR SHARED
        src/main.cpp
        # Add more source files as needed
)

# Find required libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(vulkan-lib vulkan)
find_library(GLESv1-lib GLESv1_CM)
find_library(GLESv2-lib GLESv2)

# Instead of using add_subdirectory for SDL, link directly to the prebuilt SDL library
# Assuming SDL library is already built or being used via AAR
target_link_libraries(voyagAR
        ${SDL_PATH}/libs/libSDL3.so
        ${log-lib}
        ${android-lib}
        ${vulkan-lib}
        ${GLESv1-lib}
        ${GLESv2-lib}
)

# Set C++ standard to C++17
set_target_properties(voyagAR PROPERTIES
        CXX_STANDARD 17
        CXX_STANDARD_REQUIRED YES
        CXX_EXTENSIONS NO
)