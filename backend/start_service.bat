@echo off
setlocal enabledelayedexpansion

for /f "usebackq tokens=*" %%a in ("%~dp0.env") do (
    set "line=%%a"
    for /f "tokens=1,2 delims==" %%b in ("!line!") do set "%%b=%%c"
)

cd %~dp0content-service
mvn spring-boot:run