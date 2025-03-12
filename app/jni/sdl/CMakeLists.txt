cmake_minimum_required(VERSION 3.6)

add_library(sdl3 PUBLIC)

target_include_directories(sdl3 PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/libs)