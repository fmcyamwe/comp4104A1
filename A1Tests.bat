@ECHO OFF

REM Anthony D'Angelo 100773125
REM Tsering Chopel 100649290
REM Florent Muyango 100709054

TITLE Comp 4104 A1 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Software\Languages\Java\jdk1.7.0_01\bin"
)

REM Add the folder to our Path variable. Make sure it's there.
SET "Path=%Path%;%JAVA_HOME%"

ECHO Now we'll run Question1 tests.
ECHO.
PAUSE
CALL Question1Test.bat

ECHO.
ECHO Now we'll run Question1 tests again in the hopes of seeing different orderings.
ECHO.
PAUSE
CALL Question1Test.bat

ECHO.
ECHO Now we'll run Question2 tests.
ECHO.
PAUSE
CALL Question2Test.bat

ECHO.
ECHO Now we'll run Question3 tests.
ECHO.
PAUSE
CALL Question3Test.bat

ECHO.
ECHO Now we'll run Question4 tests.
ECHO.
PAUSE
CALL Question4Test.bat

ECHO.
ECHO Now we'll run Question5 tests.
ECHO.
PAUSE
CALL Question5Test.bat

EXIT /B 0