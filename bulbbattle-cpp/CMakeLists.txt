cmake_minimum_required(VERSION 3.14.1)
project(bulbbattle)

find_package(hueplusplus)
if(NOT hueplusplus_FOUND)
    message(STATUS "-- hueplusplus not found, building it")
    add_subdirectory("${CMAKE_SOURCE_DIR}/../" "${CMAKE_BINARY_DIR}/hueplusplus")
endif()
include_directories("/usr/lib/jvm/default/include" "/usr/lib/jvm/default/include/linux")

set(SOURCES
    ${CMAKE_CURRENT_SOURCE_DIR}/hue.cpp
)

add_library(bulbbattle SHARED ${SOURCES})
set_property(TARGET bulbbattle PROPERTY CXX_STANDARD 14)
set_property(TARGET bulbbattle PROPERTY CXX_EXTENSIONS OFF)
set(CMAKE_CXX_FLAGS "-O2") ## Optimize

target_link_libraries(bulbbattle
                      pthread
                      hueplusplusstatic
)