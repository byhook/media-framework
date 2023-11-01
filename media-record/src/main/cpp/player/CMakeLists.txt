cmake_minimum_required(VERSION 3.19)

file(GLOB_RECURSE SRC_LIST ${CMAKE_CURRENT_SOURCE_DIR}/*.cc)

add_library(
        media-player
        SHARED
        ${SRC_LIST}
)

find_library(
        log-lib
        log
)

target_link_libraries(
        media-player
        ${log-lib}
)