@echo off
set JUNIT_JAR=..\lib\junit-platform-console-standalone-6.0.3.jar

if not exist out\classes mkdir out\classes
if not exist out\test-classes mkdir out\test-classes

javac -d out\classes src\*.java
if errorlevel 1 exit /b 1

javac -cp "out\classes;%JUNIT_JAR%" -d out\test-classes test\*.java
if errorlevel 1 exit /b 1



  java  -cp "out\classes;out\test-classes;%JUNIT_JAR%" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
