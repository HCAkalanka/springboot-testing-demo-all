@echo off
echo =======================================================
echo Starting SonarQube Server Locally
echo =======================================================

REM Instructions for setting up SonarQube locally:
echo 1. Download SonarQube Community Edition from: https://www.sonarqube.org/downloads/
echo 2. Extract the ZIP file to a folder like C:\sonarqube
echo 3. Navigate to bin\windows-x86-64 folder
echo 4. Run StartSonar.bat to start the server
echo 5. Once started, SonarQube will be available at http://localhost:9000
echo 6. Default login: admin/admin (you'll be asked to change password)
echo.
echo Alternative: Run SonarQube using Docker:
echo docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
echo.
echo After SonarQube is running, come back and run the analysis script.
echo.
pause