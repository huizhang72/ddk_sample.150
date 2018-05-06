#!/bin/bash

bazel build -c opt --cxxopt='--std=c++11' --fat_apk_cpu=arm64-v8a --crosstool_top=//external:android/crosstool --host_crosstool_top=@bazel_tools//tools/cpp:toolchain --config=android --cpu=arm64-v8a //app/src/main:demo
