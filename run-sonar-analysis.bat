@echo off
echo =========================================================
echo            SonarQube Analysis Script
echo =========================================================
echo.

echo Step 1: Building project and running tests...
call mvn clean compile test

echo.
echo Step 2: Running SonarQube analysis...
call mvn sonar:sonar

echo.
echo =========================================================
echo Analysis complete! 
echo.
echo Open your browser and go to: http://localhost:9000
echo.
echo Login with:
echo   Username: admin
echo   Password: admin
echo.
echo Look for project: springboot-testing-demo
echo =========================================================
pause