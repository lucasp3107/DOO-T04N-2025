@echo off
setlocal

REM 
set GSON_JAR=gson-2.13.1.jar

REM 
if not exist "%GSON_JAR%" (
    echo ERRO: O arquivo %GSON_JAR% não foi encontrado nesta pasta.
    echo Baixe de: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar
    pause
    exit /b
)

echo Compilando os arquivos Java...
javac -cp "%GSON_JAR%" *.java
if errorlevel 1 (
    echo Erro na compilação.
    pause
    exit /b
)

echo.
echo Executando o sistema...
java -cp ".;%GSON_JAR%" Main

endlocal
pause
