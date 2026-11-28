@echo off
setlocal
pushd "%~dp0" || exit /b 1

where java >nul 2>nul
if errorlevel 1 (
    echo No se ha encontrado Java. Instala una version compatible y vuelve a intentarlo.
    pause
    popd
    exit /b 1
)

java -cp "LeyendasDelCodigo.jar;lib\*" Main
if errorlevel 1 (
    echo No se ha podido iniciar el juego.
    pause
    popd
    exit /b 1
)

popd
exit /b 0
