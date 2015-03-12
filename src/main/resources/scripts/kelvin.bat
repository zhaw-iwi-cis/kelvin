@echo off
@setlocal enableextensions enabledelayedexpansion

SET APPLICATION_HOME=%~dp0
SET APPLICATION_HOME=!APPLICATION_HOME:~0,-4!

SET LIB=%APPLICATION_HOME%lib\
SET PLUGIN=%APPLICATION_HOME%plugin\
SET LOG=%APPLICATION_HOME%log\
SET APPLICATION_HOME_PROP=-Dch.zhaw.iwi.cis.kelvin.framework.applicationHome=%APPLICATION_HOME%
SET LOGGING_PROP=-Djava.util.logging.config.file=%APPLICATION_HOME%conf\logging.properties
SET ASPECTJ_AGENT=-javaagent:%LIB%aspectjweaver.jar
SET DERBY_LOG=-Dderby.stream.error.file=%LOG%/derby.log

mkdir %APPLICATION_HOME%log
mkdir %APPLICATION_HOME%db

if "%1" == "-debug" (
    set DEBUG=-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y
    shift
)

:loop
if [%1]==[] goto afterloop
set PARAMS=%PARAMS% %1
shift
goto loop
:afterloop

SET CLASSPATH="%LIB%*;%PLUGIN%*"

java -classpath %CLASSPATH% %DEBUG% %APPLICATION_HOME_PROP% %DERBY_LOG% %LOGGING_PROP% %ASPECTJ_AGENT% ch.zhaw.iwi.cis.kelvin.framework.KelvinEngine %PARAMS%