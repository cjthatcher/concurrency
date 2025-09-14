load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_binary")
load("@rules_java//java:java_binary.bzl", "java_binary")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
	name = "hello-world-library",
	srcs=glob(["src/kotlin/com/dentist/other/HelloWorld.kt"]),
)


# To build a runnable jar, we use the :<name>_deploy.jar target.
# bazel build runnable-hello-world_deploy.jar --legacy_external_runfiles
# java -jar bazel-bin/runnable-hello-world_deploy.jar
java_binary(
	name = "runnable-hello-world",
	runtime_deps = [":hello-world-library"],
	main_class = "com.dentist.other.HelloWorldKt"
)