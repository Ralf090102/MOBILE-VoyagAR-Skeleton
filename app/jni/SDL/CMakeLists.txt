cmake_minimum_required(VERSION 3.22.1)

file(GLOB_RECURSE SDL_SOURCES
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp
)

add_library(SDL STATIC ${SDL_SOURCES})

target_include_directories(SDL PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include
        ${CMAKE_CURRENT_SOURCE_DIR}/src)
