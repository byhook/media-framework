# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

include_directories(
          ${CMAKE_CURRENT_SOURCE_DIR}/include
          ${CMAKE_CURRENT_SOURCE_DIR}/libfaad)

file(GLOB_RECURSE FAAD_SRC_LIST
           ${CMAKE_CURRENT_SOURCE_DIR}/libfaad/*.c
    )

#设置编译选项，定义HAVE_CONFIG_H，解决重复typedefine问题
set(CMAKE_C_FLAGS "-DHAVE_CONFIG_H -marm -mfloat-abi=softfp -mfpu=vfp")
add_library( # Sets the name of the library.
             aacfaad2
             STATIC
             ${FAAD_SRC_LIST})


