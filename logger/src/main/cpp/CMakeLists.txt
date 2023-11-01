cmake_minimum_required(VERSION 3.19)

project("logger")

file(GLOB_RECURSE SRC_LIST
        ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)

add_library(
        logger
        SHARED
        ${SRC_LIST}
)

#到预设的目录查找 log 库 , 将找到的路径赋值给 log-lib
find_library(
        log-lib
        log
)

target_link_libraries(
        logger
        ${log-lib}
)