@ECHO OFF

REM Anthony D'Angelo 100773125
REM Tsering Chopel 100649290
REM Florent Muyango 100709054

TITLE Question 3 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Software\Languages\Java\jdk1.7.0_01\bin"
)

REM Add the folder to our Path variable. Make sure it's there.
SET "Path=%Path%;%JAVA_HOME%"

ECHO.
ECHO The next few runs will demonstrate the robustness of the program with
ECHO regards to program arguments.
ECHO First we'll try calling it with arguments from Q5 (which is many more than needed here), then
ECHO we'll try with no arguments, then we'll try with negative integers, then we'll try with letters
ECHO instead of numbers, then we'll try with only one argument.

ECHO.
PAUSE

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question3 -C -1 -B -1 -g -1 -c -1 -W -1 -R -4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question3 -t -1 -r -2
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question3 -t r -r u
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question3 -r 5
ECHO.
ECHO.

ECHO Now we'll start the tests with valid arguments.
PAUSE



ECHO.
PAUSE

EXIT /B 0