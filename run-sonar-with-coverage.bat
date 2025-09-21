@echo off
echo 🚀 Running SonarQube Analysis with Code Coverage Integration
echo.

REM Set Maven path
set "MAVEN_PATH=E:\ContactBookApplicationQA\new\tools\apache-maven-3.9.9\bin\mvn.cmd"

REM Navigate to project directory
cd /d "E:\ContactBookApplicationQA\new\springboot-testing-demo-all"

echo 🧹 Cleaning previous builds and reports...
"%MAVEN_PATH%" clean

echo.
echo 🧪 Running tests with JaCoCo coverage...
"%MAVEN_PATH%" test

echo.
echo 📊 Generating JaCoCo coverage reports...
"%MAVEN_PATH%" jacoco:report

echo.
echo 🔍 Running SonarQube analysis with coverage data...
"%MAVEN_PATH%" sonar:sonar ^
  -Dsonar.projectKey=springboot-testing-demo ^
  -Dsonar.host.url=http://localhost:9000 ^
  -Dsonar.token=sqp_0a3e19fe4d82549bc66d33975a2b9eb760720835 ^
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
  -Dsonar.java.coveragePlugin=jacoco

echo.
if %ERRORLEVEL% EQU 0 (
    echo ✅ SonarQube analysis completed successfully!
    echo 📊 Coverage reports have been uploaded to SonarQube
    echo 🌐 View results at: http://localhost:9000/dashboard?id=springboot-testing-demo
    echo.
    echo 📈 Coverage report also available locally at:
    echo    target\site\jacoco\index.html
) else (
    echo ❌ SonarQube analysis failed!
    echo Please check the logs above for errors.
)

echo.
echo 📊 Opening local coverage report...
start target\site\jacoco\index.html

pause