cmake_minimum_required(VERSION 3.19)

file(GLOB_RECURSE SRC_LIST ${CMAKE_CURRENT_SOURCE_DIR}/*.cc)

add_library(
        media-recorder
        SHARED
        ${SRC_LIST}
)

find_package(oboe REQUIRED CONFIG)

target_link_libraries(
        media-recorder
        android
        log
        atomic
        OpenSLES
        aaudio
        oboe::oboe
)