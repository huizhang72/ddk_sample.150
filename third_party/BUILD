package(default_visibility = ["//visibility:public"])

java_library(
    name = "jsr250_annotations",
    exports = ["@javax_annotation_jsr250_api//jar"],
)

java_library(
    name = "jsr305_annotations",
    exports = ["@com_google_code_findbugs_jsr305//jar"],
)

java_library(
    name = "jsr330_inject",
    exports = ["@javax_inject_javax_inject//jar"],
)

java_library(
    name = "okhttp",
    exports = ["@com_squareup_okhttp_okhttp//jar"],
)

java_library(
    name = "error_prone_annotations",
    tags = ["maven:compile_only"],
    exports = ["@com_google_errorprone_error_prone_annotations//jar"],
)

java_library(
    name = "junit",
    testonly = 1,
    exports = [
        "@junit_junit//jar",
        "@org_hamcrest_hamcrest_core//jar",
    ],
)

java_library(
    name = "truth",
    testonly = 1,
    exports = ["@com_google_truth_truth//jar"],
)

java_library(
    name = "truth8",
    testonly = 1,
    exports = [
        ":truth",
        "@com_google_truth_extensions_truth_java8_extension//jar",
    ],
)

java_library(
    name = "guava",
    exports = ["@com_google_guava_guava//jar"],
)

java_library(
    name = "guava_android",
    exports = ["@com_google_guava_guava_android//jar"],
)

java_library(
    name = "guava_testlib",
    testonly = 1,
    exports = ["@com_google_guava_guava_testlib//jar"],
)

java_library(
    name = "auto_common",
    tags = ["maven:shaded"],
    exports = ["@com_google_auto_auto_common//jar"],
)

java_library(
    name = "google_java_format",
    exports = [
        "@com_google_googlejavaformat_google_java_format//jar",
    ],
    runtime_deps = [
        ":guava",
        "@com_google_errorprone_javac//jar",
    ],
)

java_library(
    name = "javapoet",
    exports = ["@com_squareup_javapoet//jar"],
)

java_library(
    name = "joda_time",
    exports = ["@joda_time_joda_time//jar"],
)

java_library(
    name = "mockito",
    testonly = 1,
    exports = ["@org_mockito_mockito_core//jar"],
    runtime_deps = [
        "@org_hamcrest_hamcrest_core//jar",
        "@org_objenesis_objenesis//jar",
    ],
)

java_library(
    name = "grpc",
    exports = ["@io_grpc_grpc_all//jar"],
)

java_library(
    name = "grpc_core",
    exports = ["@io_grpc_grpc_core//jar"],
)

java_library(
    name = "grpc_netty",
    exports = ["@io_grpc_grpc_netty//jar"],
)

java_library(
    name = "grpc_context",
    exports = ["@io_grpc_grpc_context//jar"],
)

java_library(
    name = "grpc_protobuf",
    exports = ["@io_grpc_grpc_protobuf//jar"],
)

java_library(
    name = "grpc_stub",
    exports = ["@io_grpc_grpc_stub//jar"],
)

java_library(
    name = "protostuff_core",
    exports = ["@io_protostuff_protostuff_core//jar"],
)

java_library(
    name = "protobuf_java_format",
    exports = ["@com_googlecode_protobuf_java_format_protobuf_java_format//jar"],
)

java_library(
    name = "otto",
    exports = ["@com_squareup_otto//jar"],
)

java_import(
    name = "record",
    jars = ["record/record_proto-lite-src.jar"],
)

java_library(
    name = "protobuf",
    exports = ["@com_google_protobuf_protobuf_java//jar"],
)

java_library(
    name = "protobuf_lite",
    exports = ["@com_google_protobuf_protobuf_lite_java//jar"],
)

java_library(
    name = "com_googlecode_protobuf_java_format_protobuf_java_format",
    exports = ["@com_googlecode_protobuf_java_format_protobuf_java_format//jar"],
)

java_library(
    name = "volley",
    exports = ["@com_mcxiaoke_volley_library//jar"],
)

java_library(
    name = "android_arch_lifecycle_common",
    exports = ["@android_arch_lifecycle_common//jar"],
)

java_library(
    name = "android_arch_core_common",
    exports = ["@android_arch_core_common//jar"],
)

java_library(
    name = "com_android_support_support_annotations",
    exports = ["@com_android_support_support_annotations//jar"],
)

java_library(
    name = "com_android_support_constraint_constraint_layout_solver",
    exports = ["@com_android_support_constraint_constraint_layout_solver//jar"],
)

java_library(
    name = "compile_testing",
    testonly = 1,
    exports = [
        "@com_google_testing_compile_compile_testing//jar",
    ],
    runtime_deps = [
        ":auto_value",
        ":jsr305_annotations",
        "//third_party:error_prone_annotations",
        "//third_party:guava",
        "//third_party:junit",
        "//third_party:truth",
        "@local_jdk//:lib/tools.jar",
    ],
)
