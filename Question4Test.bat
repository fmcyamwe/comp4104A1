@ECHO OFF

REM Anthony D'Angelo 100773125
REM Tsering Chopel 100649290
REM Florent Muyango 100709054

TITLE Question 4 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Software\Languages\Java\jdk1.7.0_01\bin"
)

REM Add the folder to our Path variable. Make sure it's there.
SET "Path=%Path%;%JAVA_HOME%"

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question4
ECHO.
ECHO.

ECHO Done Q4 tests.

ECHO.
PAUSE

EXIT /B 0