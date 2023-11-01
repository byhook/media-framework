cmake_minimum_required(VERSION 3.19)

project(encode)

file(GLOB_RECURSE SRC_LIST ${CMAKE_CURRENT_SOURCE_DIR}/*.cc)

add_library(
        audio-encoder
        SHARED
        ${SRC_LIST}
)

find_package(lame REQUIRED CONFIG)

target_link_libraries(
        audio-encoder
        android
        log
        lame::lame.so
)