cmake_minimum_required(VERSION 3.19)

include_directories(
        ${CMAKE_CURRENT_SOURCE_DIR}/common
)

set(LIB_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR})

add_subdirectory(${LIB_SRC_DIR}/recorder)
add_subdirectory(${LIB_SRC_DIR}/player)