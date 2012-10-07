@ECHO OFF
TITLE Comp 4104 A1 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Program Files\Java\jdk1.7.0_07\bin"
REM Add the folder to our Path variable
SET "Path=%Path%;%JAVA_HOME%"
)

Question5Test.bat

EXIT /B 0