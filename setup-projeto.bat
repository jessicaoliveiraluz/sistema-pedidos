@echo off
REM === CONFIGURAÇÕES ===
SET "JDK_PATH=C:\Users\Public\jdk-17.0.16"
SET "GRADLEW_PATH=%~dp0gradlew"

REM === VERIFICA JDK ===
IF NOT EXIST "%JDK_PATH%\bin\java.exe" (
    echo ❌ JDK não encontrado em: %JDK_PATH%
    pause
    exit /b 1
)

REM === CONFIGURA JAVA_HOME ===
SET JAVA_HOME=%JDK_PATH%
SET PATH=%JAVA_HOME%\bin;%PATH%

echo ✅ JAVA configurado em: %JAVA_HOME%
java -version

REM === EXCLUI CACHE DO GRADLE ===
echo 🧹 Limpando cache local do Gradle...
RMDIR /S /Q "%USERPROFILE%\.gradle\caches"

REM === EXECUTA BUILD ===
echo 🚀 Executando build do projeto...
cd /d "%~dp0"
call "%GRADLEW_PATH%" clean build

pause
