@ECHO OFF

REM Anthony D'Angelo 100773125
REM Tsering Chopel 100649290
REM Florent Muyango 100709054

TITLE Question 5 Tests

REM Check if we have a path to the Java binaries, 
REM otherwise we set it ourselves to the default.
if "%JAVA_HOME%"=="" (
SET "JAVA_HOME=C:\Program Files\Java\jdk1.7.0_07\bin"
REM Add the folder to our Path variable
SET "Path=%Path%;%JAVA_HOME%"
)

ECHO.
ECHO The next few runs will demonstrate the robustness of the program with
ECHO regards to program arguments.
ECHO First we'll try calling it with no waiting room chairs, then with
ECHO negative numbers, then with letters instead of numbers, then with
ECHO no arguments, then with no barbers.
ECHO.
PAUSE
ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 1 -B 1 -g 1 -c 1 -W 0 -R 4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C -1 -B -1 -g -1 -c -1 -W -1 -R -4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C k -B h -g h -c h -W h -R h
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 1 -B 0 -g 1 -c 1 -W 1 -R 2
ECHO.
ECHO.

ECHO Now we'll start the tests with valid arguments.
PAUSE

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 1 -B 1 -g 1 -c 1 -W 1 -R 4
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 6 -B 1 -g 1 -c 1 -W 1 -R 4
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
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 3 -B 1 -g 1 -c 1 -W 2 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul


ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 2 -B 4 -g 1 -c 1 -W 1 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 8 -B 6 -g 1 -c 1 -W 4 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 8 -B 5 -g 1 -c 1 -W 4 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 2 -B 2 -g 1 -c 1 -W 7 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 2 -B 8 -g 1 -c 1 -W 3 -R 3
ECHO.
ECHO.
ECHO Press any key to continue to the next test run.
PAUSE>nul

ECHO.
java -cp bin edu.carleton.comp4104.assignment1.Question5 -C 9 -B 3 -g 1 -c 1 -W 6 -R 3
ECHO.
ECHO.
ECHO Done Q5 tests.

REM This is how you prompt something --> SET /P myVar=Enter Your char 

ECHO.
PAUSE

EXIT /B 0