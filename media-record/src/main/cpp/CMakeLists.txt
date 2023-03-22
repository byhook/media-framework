cmake_minimum_required(VERSION 3.18.1)

include_directories(
        ${CMAKE_CURRENT_SOURCE_DIR}/include/oboe
)

file(GLOB_RECURSE SRC_LIST
        ${CMAKE_CURRENT_SOURCE_DIR}/record/*.cc)

add_library(
        media-record
        SHARED
        ${SRC_LIST}
)

find_library(
        log-lib
        log
)

target_link_libraries(
        media-record
        ${log-lib}
)