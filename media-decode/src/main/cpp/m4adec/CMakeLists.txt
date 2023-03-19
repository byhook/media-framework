# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

include_directories(
        ${CMAKE_CURRENT_SOURCE_DIR}/../faad2/include
        #${CMAKE_CURRENT_SOURCE_DIR}/../mp4ff
        ${CMAKE_CURRENT_SOURCE_DIR})


file(GLOB_RECURSE DEC_SRC_LIST
        ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp
        )

add_library( # Sets the name of the library.
        m4adec
        SHARED
        ${DEC_SRC_LIST})

find_library(log-lib
        log)

target_link_libraries( # Specifies the target library.
        m4adec
        aacfaad2
        mp4ff
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

