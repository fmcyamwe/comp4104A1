@ECHO OFF

REM Anthony D'Angelo 100773125
REM Tsering Chopel 100649290
REM Florent Muyango 100709054

TITLE Question 2 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Software\Languages\Java\jdk1.7.0_01\bin"
)

REM Add the folder to our Path variable. Make sure it's there.
SET "Path=%Path%;%JAVA_HOME%"

ECHO.
ECHO Question 2 not completed.
ECHO.
ECHO.

ECHO Done Q2 tests.

ECHO.
PAUSE

EXIT /B 0