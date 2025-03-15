add_definitions(-DSDL_PLATFORM_ANDROID=1)

cmake_minimum_required(VERSION 3.22.1)

file(GLOB_RECURSE SDL_SOURCES
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp
)

# Platform Exclusions
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

# Unnecessary Component Exclusions
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/testgui/.*")    # HIDAPI test GUI
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/hidtest/.*")    # HIDAPI tests
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/libusb/.*")     # libusb implementation
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/windows/.*")    # Windows HIDAPI
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*hidapi/mac/.*")        # Mac HIDAPI
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*dummy/.*")             # Dummy implementations
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*thread/generic.*")     # Generic Thread implementations
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*process/.*")           # SDL Process Management implementations
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*test/.*")              # Test files
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*tests/.*")             # Test files (alternative path)
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*testing/.*")           # Test files (alternative path)
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*time/android/.*")      # Android time
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*locale/unix/.*")       # Unix locale
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*dialog/unix/.*")       # Unix dialog implementations
list(FILTER SDL_SOURCES EXCLUDE REGEX ".*misc/unix/.*")         # Unix misc implementations

add_library(SDL3 SHARED ${SDL_SOURCES})

target_include_directories(SDL3 PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include/build_config
        ${CMAKE_CURRENT_SOURCE_DIR}/include/SDL3
        ${CMAKE_CURRENT_SOURCE_DIR}/src)

# Compiler flags for C and C++
target_compile_options(SDL3 PRIVATE
        -DGL_GLEXT_PROTOTYPES
        -Wall -Wextra
        -Wdocumentation
        -Wmissing-prototypes
        -Wunreachable-code-break
        -Wunneeded-internal-declaration
        -Wmissing-variable-declarations
        -Wfloat-conversion
        -Wshorten-64-to-32
        -Wunreachable-code-return
        -Wshift-sign-overflow
        -Wstrict-prototypes
        -Wkeyword-macro
        -Wno-unused-parameter
        -Wno-sign-compare
        -Wno-missing-variable-declarations
        -Wno-missing-prototypes
        $<$<COMPILE_LANGUAGE:CXX>:-std=gnu++20>
)

# Link required Android system libraries
target_link_libraries(SDL3 PRIVATE
        dl          # Dynamic Linking
        OpenSLES    # OpenSL ES audio
        GLESv2      # OpenGL ES 2.0
        EGL         # EGL (needed for OpenGL ES context creation)
        android     # Android system functions
        log         # Android logging
)

target_compile_definitions(SDL3 PRIVATE
        ANDROID
        __ANDROID__
        SDL_VIDEO_DRIVER_ANDROID=1
        SDL_VIDEO_OPENGL_ES=1
        SDL_VIDEO_OPENGL_ES2=1
        SDL_VIDEO_VULKAN=1
        SDL_TIMER_UNIX=1
        SDL_CLOCK_MONOTONIC=1
        SDL_LOCALE_ANDROID=1
        SDL_THREAD_PTHREAD=1
        SDL_PROCESS_DISABLED=1
        SDL_TEST_DISABLED=1
)
