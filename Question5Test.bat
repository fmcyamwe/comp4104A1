@ECHO OFF

TITLE Question 5 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Program Files\Java\jdk1.7.0_07\bin"
REM Add the folder to our Path variable
SET "Path=%Path%;%JAVA_HOME%"
)

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 1 -B 1 -g 1 -c 1 -W 1 -R 4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 1 -B 0 -g 1 -c 1 -W 1 -R 2
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 0 -B 1 -g 1 -c 1 -W 1 -R 2
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 3 -B 1 -g 1 -c 1 -W 2 -R 4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul


ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 2 -B 4 -g 1 -c 1 -W 1 -R 4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

REM SET /P myVar=Enter Your char 

ECHO.
PAUSE

EXIT /B 0