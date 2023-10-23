cmake_minimum_required(VERSION 3.18.1)

file(GLOB_RECURSE SRC_LIST ${CMAKE_CURRENT_SOURCE_DIR}/*.cc)

add_library(
        media-recorder
        SHARED
        ${SRC_LIST}
)

target_link_libraries(
        media-recorder
        android
        log
        OpenSLES
        atomic
        aaudio
)