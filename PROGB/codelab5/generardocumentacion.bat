@echo off
setlocal

pushd "%~dp0" || exit /b 1

javadoc -d docs -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 src\*.java
if errorlevel 1 (
    echo Error al generar la documentacion.
    popd
    exit /b 1
)

echo Documentacion generada en docs\index.html
popd
exit /b 0