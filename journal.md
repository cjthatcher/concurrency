Okay, so, learning / revisiting Bazel by building a Kotlin / Java project from scratch.

The first concept is a `workspace`. A `workspace` is the big atomic unit of a bazel project. We create a `workspace` by
putting a MODULE.bazel file and a BUILD file in a directory. Everything in and below this directory is part of this
bazel `workspace`.

Now, anything with a BUILD file _below_ the workspace root is going to be a `package`. Is the workspace a package?
Probably.

Note I am using bazelisk, which is a fun wrapper _around_ bazel. It will keep it up to date, etc. etc.

So all my commands are bazelisk build instead of bazel build.

The MODULE.bazel file contains the name of the module, as well as some dependencies. Stuff like "Hey, here's where you'
re going to load the build targets you `load` in the BUILD files from..."

Argh, my build was failing because I did not have JAVA_HOME set in my environment variables. Fixed that.

K, looks like kotlin and bazel (as of May 14, 2025) have a problem. I need to add `--legacy_external_runfiles` to the
end of my commands. It's having a hard time location some annotations jar that has been a nightmare in the past. 

K, September 13 update:
1) Want to make a .jar with a main method from a Kotlin file?
2) You need to make a kotlin_jvm_library build target
3) Then have a java_binary build target depent on (2) (as a runtime dep probably)
4) Set the "main_class" attribute to --> "com.dentist.other.MyClassNameKt". We use "Kt" to point to top-level functions outside the class. Technically it's possible to point to a main method of a class (like, one inside a companion object) but I can't get that to work right now.
5) Run the bazel build "my-java-binary-build-target-name_deploy.jar" target. That's a hidden target we get from java_binary.
6) You can then java -jar the my-java-binary-build-target-name.jar located in /bazel-bin.

Note: Don't forget to add the `--legacy_external_runfiles` arg to the end of your targets that include any Kotlin.
Note: You may need to install MSYS2 in order to get any of these built jars to run on Windows. 
