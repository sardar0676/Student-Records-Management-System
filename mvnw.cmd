@echo off
rem Simple Maven wrapper. Uses Maven if installed, otherwise downloads it once.
setlocal
set MAVEN_VERSION=3.9.9
cd /d "%~dp0"
where mvn >nul 2>nul
if errorlevel 1 goto download
call mvn %*
exit /b %ERRORLEVEL%
:download
set DIST_ROOT=%USERPROFILE%\.m2\wrapper\dists
set MVN_HOME=%DIST_ROOT%\apache-maven-%MAVEN_VERSION%
if exist "%MVN_HOME%\bin\mvn.cmd" goto run
echo Downloading Maven %MAVEN_VERSION% (one time only)...
if not exist "%DIST_ROOT%" mkdir "%DIST_ROOT%"
powershell -NoProfile -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol=[Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip -OutFile \"%DIST_ROOT%\maven.zip\"; Expand-Archive -Force \"%DIST_ROOT%\maven.zip\" \"%DIST_ROOT%\"; Remove-Item \"%DIST_ROOT%\maven.zip\""
:run
call "%MVN_HOME%\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%
