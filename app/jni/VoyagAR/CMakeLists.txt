cmake_minimum_required(VERSION 3.22.1)

find_library(log-lib log)
find_library(android-lib android)
find_library(SDL3 SDL3)

add_library(${CMAKE_PROJECT_NAME} SHARED ${CMAKE_CURRENT_SOURCE_DIR}/src/main.cpp)

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}/include
        ${CMAKE_CURRENT_SOURCE_DIR}/../SDL/include)

target_link_libraries(${CMAKE_PROJECT_NAME} PRIVATE
        SDL3
        ${log-lib}
        ${android-lib})
