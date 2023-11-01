cmake_minimum_required(VERSION 3.18.1)

file(GLOB_RECURSE SRC_LIST ${CMAKE_CURRENT_SOURCE_DIR}/*.cc)

add_library(
        lame-encoder
        SHARED
        ${SRC_LIST}
)

target_link_libraries(
        lame-encoder
        android
        log
)