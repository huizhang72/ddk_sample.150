package(default_visibility = [
    "//visibility:public",
])

cc_import(
  name = "libhiai.so",
  shared_library = "arm64-v8a/libhiai.so",
)

cc_import(
  name = "libtensorflow_inference.so",
  shared_library = "arm64-v8a/libtensorflow_inference.so",
)

cc_library(
    name = "libhiai",
    srcs = [
        "arm64-v8a/libhiai.so",
    ],
)

cc_library(
    name = "libtensorflow_inference",
    srcs = [
        "arm64-v8a/libtensorflow_inference.so",
    ],
)

java_import(
  name = "android_tensorflow_inference_jar",
  jars = [
       "libandroid_tensorflow_inference_java.jar",
  ],
)
