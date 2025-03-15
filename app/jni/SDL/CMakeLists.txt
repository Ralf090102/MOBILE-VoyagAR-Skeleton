cmake_minimum_required(VERSION 3.22.1)

if (NOT ANDROID)
    message(FATAL_ERROR "SDL is only being compiled for Android!")
endif()

file(GLOB_RECURSE SDL_SOURCES
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp
)

# Platform exclusions
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*ps2/.*")               # PlayStation 2
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*windows/.*")           # Windows
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*n3ds/.*")              # Nintendo 3DS
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*vita/.*")              # PS Vita
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*psp/.*")               # PSP
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*haiku/.*")             # Haiku OS
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*macos/.*")             # macOS
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*ios/.*")               # iOS
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*openbsd/.*")           # OpenBSD
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*freebsd/.*")           # FreeBSD
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*netbsd/.*")            # NetBSD
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*riscos/.*")            # RISC OS
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*linux/.*")             # Linux-specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*gdk/.*")               # GNOME Development Kit
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*emscripten/.*")        # Emscripten/WebAssembly
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*qnx/.*")               # QNX/BlackBerry
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*raspberry/.*")         # Raspberry Pi specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*kms/.*")               # Linux KMS specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*wayland/.*")           # Wayland specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*x11/.*")               # X11 specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*cocoa/.*")             # macOS Cocoa specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*vivante/.*")           # Vivante specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*directfb/.*")          # DirectFB specific
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*unix/.*")              # Unix-specific code

# Test and unnecessary component exclusions
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/testgui/.*")    # Remove HIDAPI test GUI
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/hidtest/.*")    # Remove HIDAPI tests
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/libusb/.*")     # Remove libusb implementation
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/windows/.*")    # Remove Windows HIDAPI
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/mac/.*")        # Remove Mac HIDAPI


add_library(SDL3 SHARED ${SDL_SOURCES})

target_include_directories(SDL3 PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include/build_config
        ${CMAKE_CURRENT_SOURCE_DIR}/include/SDL3
        ${CMAKE_CURRENT_SOURCE_DIR}/src)

# Link required Android system libraries
target_link_libraries(SDL3 PRIVATE
        OpenSLES    # OpenSL ES audio
        android     # Android system functions
        log         # Android logging
        GLESv2     # OpenGL ES 2.0
        EGL        # EGL (needed for OpenGL ES context creation)
)

target_compile_definitions(SDL3 PRIVATE
        ANDROID
        __ANDROID__
        SDL_VIDEO_DRIVER_ANDROID=1
        SDL_VIDEO_OPENGL_ES=1
        SDL_VIDEO_OPENGL_ES2=1
        SDL_VIDEO_VULKAN=1
)
