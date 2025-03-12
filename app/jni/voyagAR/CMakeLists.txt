cmake_minimum_required(VERSION 3.6)

project(MOBILE_VOYAGAR)

#find sdl3
find_package(SDL3 REQUIRED CONFIG)

#define executable
add_executable(main src/main.cpp)

# Include directories
target_include_directories(main PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/include)

# Link SDL3
target_link_libraries(main PRIVATE SDL3::SDL3)