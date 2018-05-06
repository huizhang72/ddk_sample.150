# Set the $ANDROID_HOME and $ANDROID_NDK_HOME environment.
android_sdk_repository(name = "androidsdk")
android_ndk_repository(name = "androidndk")

# for maven_aar
load("@bazel_tools//tools/build_defs/repo:maven_rules.bzl", "maven_aar")

# for google maven server
maven_server(
    name = "google_maven_server",
    url = "https://maven.google.com",
)

maven_jar(
    name = "javax_annotation_jsr250_api",
    artifact = "javax.annotation:jsr250-api:1.0",
    sha1 = "5025422767732a1ab45d93abfea846513d742dcf",
)

maven_jar(
    name = "com_google_code_findbugs_jsr305",
    artifact = "com.google.code.findbugs:jsr305:3.0.1",
    sha1 = "f7be08ec23c21485b9b5a1cf1654c2ec8c58168d",
)

maven_jar(
    name = "javax_inject_javax_inject",
    artifact = "javax.inject:javax.inject:1",
    sha1 = "6975da39a7040257bd51d21a231b76c915872d38",
)

maven_jar(
    name = "javax_inject_javax_inject_tck",
    artifact = "javax.inject:javax.inject-tck:1",
    sha1 = "bb0090d50219c265be40fcc8e034dae37fa7be99",
)

maven_jar(
    name = "com_google_guava_guava",
    artifact = "com.google.guava:guava:23.0",
    sha1 = "c947004bb13d18182be60077ade044099e4f26f1",
)

maven_jar(
    name = "com_google_guava_guava_android",
    artifact = "com.google.guava:guava:23.0-android",
    sha1 = "024e15a141252eb80c53381d78ef61efd1353763",
)

maven_jar(
    name = "com_google_guava_guava_testlib",
    artifact = "com.google.guava:guava-testlib:21.0-rc1",
    sha1 = "13f0f0dce4e710bb0bb791bd07f6e9858670a865",
)

maven_jar(
    name = "com_googlecode_protobuf_java_format_protobuf_java_format",
    artifact = "com.googlecode.protobuf-java-format:protobuf-java-format:1.4",
)

maven_jar(
    name = "com_google_errorprone_javac",
    artifact = "com.google.errorprone:javac-shaded:9-dev-r4023-3",
    sha1 = "72b688efd290280a0afde5f9892b0fde6f362d1d",
)

maven_jar(
    name = "com_google_googlejavaformat_google_java_format",
    artifact = "com.google.googlejavaformat:google-java-format:1.4",
    sha1 = "c2f8925850e17caa6da0ed1891a9e9de9414c062",
)

maven_jar(
    name = "com_google_auto_auto_common",
    artifact = "com.google.auto:auto-common:0.8",
    sha1 = "c6f7af0e57b9d69d81b05434ef9f3c5610d498c4",
)

maven_jar(
    name = "com_google_auto_factory_auto_factory",
    artifact = "com.google.auto.factory:auto-factory:1.0-beta3",
    sha1 = "99b2ffe0e41abbd4cc42bf3836276e7174c4929d",
)

maven_jar(
    name = "com_squareup_javawriter",
    artifact = "com.squareup:javawriter:2.5.1",
    sha1 = "54c87b3d91238e5b58e1a436d4916eee680ec959",
)

maven_jar(
    name = "com_google_auto_service_auto_service",
    artifact = "com.google.auto.service:auto-service:1.0-rc2",
    sha1 = "51033a5b8fcf7039159e35b6878f106ccd5fb35f",
)

maven_jar(
    name = "com_google_auto_value_auto_value",
    artifact = "com.google.auto.value:auto-value:1.4-rc1",
    sha1 = "9347939002003a7a3c3af48271fc2c18734528a4",
)

maven_jar(
    name = "com_google_errorprone_error_prone_annotations",
    artifact = "com.google.errorprone:error_prone_annotations:2.0.12",
    sha1 = "8530d22d4ae8419e799d5a5234e0d2c0dcf15d4b",
)

maven_jar(
    name = "io_protostuff_protostuff_core",
    artifact = "io.protostuff:protostuff-core:1.6.0",
)

maven_jar(
    name = "junit_junit",
    artifact = "junit:junit:4.11",
    sha1 = "4e031bb61df09069aeb2bffb4019e7a5034a4ee0",
)

maven_jar(
    name = "com_google_testing_compile_compile_testing",
    artifact = "com.google.testing.compile:compile-testing:0.11",
    sha1 = "bff5d5aa61e6384b9dd4f5f7bb97a921081f4e1c",
)

maven_jar(
    name = "org_mockito_mockito_core",
    artifact = "org.mockito:mockito-core:1.9.5",
    sha1 = "c3264abeea62c4d2f367e21484fbb40c7e256393",
)

maven_jar(
    name = "org_hamcrest_hamcrest_core",
    artifact = "org.hamcrest:hamcrest-core:1.3",
    sha1 = "42a25dc3219429f0e5d060061f71acb49bf010a0",
)

maven_jar(
    name = "org_objenesis_objenesis",
    artifact = "org.objenesis:objenesis:1.0",
    sha1 = "9b473564e792c2bdf1449da1f0b1b5bff9805704",
)

maven_jar(
    name = "com_google_truth_truth",
    artifact = "com.google.truth:truth:0.30",
    sha1 = "9d591b5a66eda81f0b88cf1c748ab8853d99b18b",
)

maven_jar(
    name = "com_google_truth_extensions_truth_java8_extension",
    artifact = "com.google.truth.extensions:truth-java8-extension:0.30",
    sha1 = "f3bb5e49001a9b575bcdef9aa8417b6d1ef35509",
)

maven_jar(
    name = "com_squareup_javapoet",
    artifact = "com.squareup:javapoet:1.8.0",
    sha1 = "e858dc62ef484048540d27d36f3ec2177a3fa9b1",
)

maven_jar(
    name = "io_grpc_grpc_core",
    artifact = "io.grpc:grpc-core:1.2.0",
    sha1 = "f12a213e2b59a0615df2cc9bed35dc15fd2fee37",
)

maven_jar(
    name = "io_grpc_grpc_netty",
    artifact = "io.grpc:grpc-netty:1.2.0",
    sha1 = "e2682d2dc052898f87433e7a6d03d104ef98df74",
)

maven_jar(
    name = "io_grpc_grpc_context",
    artifact = "io.grpc:grpc-context:1.2.0",
    sha1 = "1932db544cbb427bc18f902c7ebbb3f7e44991df",
)

maven_jar(
    name = "io_grpc_grpc_protobuf",
    artifact = "io.grpc:grpc-protobuf:1.2.0",
    sha1 = "2676852d2dbd20155d9b1a940a456eae5b7445f0",
)

maven_jar(
    name = "io_grpc_grpc_stub",
    artifact = "io.grpc:grpc-stub:1.2.0",
    sha1 = "964dda53b3085bfd17c7aaf51495f9efc8bda36c",
)

maven_jar(
    name = "io_grpc_grpc_all",
    artifact = "io.grpc:grpc-all:1.2.0",
    sha1 = "f32006a1245dfa2d68bf92a1b4cc01831889c95b",
)

maven_jar(
    name = "joda_time_joda_time",
    artifact = "joda-time:joda-time:2.9.9",
    sha1 = "f7b520c458572890807d143670c9b24f4de90897",
)

maven_jar(
    name = "com_squareup_otto",
    artifact = "com.squareup:otto:1.3.4",
    sha1 = "4d72fb811c7b3c0e7f412112020d4430f044e510",
)

maven_jar(
    name = "com_google_protobuf_protobuf_java",
    artifact = "com.google.protobuf:protobuf-java:3.2.0",
    sha1 = "62ccf171a106ff6791507f2d5364c275f9a3131d",
)

maven_jar(
    name = "com_google_protobuf_protobuf_lite_java",
    artifact = "com.google.protobuf:protobuf-lite:3.0.0",
    sha1 = "5dd2651003dcaafcdb73ff3c5230104dce215c19",
)

maven_jar(
    name = "com_mcxiaoke_volley_library",
    artifact = "com.mcxiaoke.volley:library:1.0.19",
    sha1 = "a8f23f65fc1e522ee4a1a697ee569901a46741fa",
)

maven_jar(
    name = "com_squareup_okhttp_okhttp",
    artifact = "com.squareup.okhttp:okhttp:2.7.2",
    sha1 = "20f6463eb19ac61960c5d91a094c2f4f0727dc2e",
)

maven_jar(
    name = "com_squareup_okhttp3_okhttp",
    artifact = "com.squareup.okhttp3:okhttp:3.4.2",
    sha1 = "ccde00f7ccc77af5a6d5752e2cb21f6d8998289f",
)

maven_jar(
    name = "com_squareup_retrofit2_retrofit",
    artifact = "com.squareup.retrofit2:retrofit:2.1.0",
    sha1 = "2de7cd8b95b7021b1d597f049bcb422055119f2c",
)

maven_jar(
    name = "com_google_code_gson_gson",
    artifact = "com.google.code.gson:gson:2.7",
    sha1 = "751f548c85fa49f330cecbb1875893f971b33c4e",
)

maven_jar(
    name = "com_squareup_okio_okio",
    artifact = "com.squareup.okio:okio:1.9.0",
    sha1 = "f824591a0016efbaeddb8300bee54832a1398cfa",
)

maven_jar(
    name = "android_arch_lifecycle_common",
    artifact = "android.arch.lifecycle:common:1.0.3",
    server = "google_maven_server",
    sha1 = "7d7f60c4783872861222166f6164215f8951c7b1",
)

maven_jar(
    name = "com_android_support_constraint_constraint_layout_solver",
    artifact = "com.android.support.constraint:constraint-layout-solver:1.1.0-beta1",
    server = "google_maven_server",
    sha1 = "04205dd8c33ada1468ab377afff0be95304ef72a",
)

maven_aar(
    name = "com_android_support_constraint_constraint_layout",
    artifact = "com.android.support.constraint:constraint-layout:1.1.0-beta1",
    settings = "//third_party/settings:google-maven.xml",
)

maven_jar(
    name = "com_android_support_support_annotations",
    artifact = "com.android.support:support-annotations:26.1.0",
    server = "google_maven_server",
    sha1 = "0814258103cf26a15fcc26ecce35f5b7d24b73f8",
)

maven_aar(
    name = "com_android_support_appcompat_v7",
    artifact = "com.android.support:appcompat-v7:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_cardview",
    artifact = "com.android.support:cardview-v7:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_recyclerview",
    artifact = "com.android.support:recyclerview-v7:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_compat",
    artifact = "com.android.support:support-compat:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_v4",
    artifact = "com.android.support:support-v4:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_core_ui",
    artifact = "com.android.support:support-core-ui:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_fragment",
    artifact = "com.android.support:support-fragment:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_core_ui",
    artifact = "com.android.support:support-core-ui:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_vector_drawable",
    artifact = "com.android.support:support-vector-drawable:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_core_utils",
    artifact = "com.android.support:support-core-utils:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "com_android_support_support_fragment",
    artifact = "com.android.support:support-fragment:26.1.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_aar(
    name = "android_arch_lifecycle_runtime",
    artifact = "android.arch.lifecycle:runtime:1.0.0",
    settings = "//third_party/settings:google-maven.xml",
)

maven_jar(
    name = "android_arch_core_common",
    artifact = "android.arch.core:common:1.0.0",
    server = "google_maven_server",
    sha1 = "a2d487452376193fc8c103dd2b9bd5f2b1b44563",
)

maven_aar(
    name = "com_android_support_design",
    artifact = "com.android.support:design:26.1.0",
    deps = [
        "@com_android_support_appcompat_v7//aar",
    ],
    settings = "//third_party/settings:google-maven.xml",
)
