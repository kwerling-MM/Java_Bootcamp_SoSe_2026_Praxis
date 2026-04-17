#!/bin/bash

JUNIT_JAR="..\lib/junit-platform-console-standalone-6.0.3.jar"

mkdir -p out/classes
mkdir -p out/test-classes

javac -d out/classes src/Jorth.java || exit 1
javac -cp "out/classes:$JUNIT_JAR" -d out/test-classes test/JorthTest.java || exit 1


java  -cp "out\classes;out\test-classes;%JUNIT_JAR%" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
