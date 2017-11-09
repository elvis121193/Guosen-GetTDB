@echo off

rem ÉèÖÃJNI¼ÓÔØÂ·¾¶

PATH=%PATH%;x64

rem java -classpath demo.jar;tdbapi.jar;bin tdbapi.Demo ip port user password

@echo on

java -classpath demo.jar;tdbapi.jar;bin tdbapi.Demo 10.100.4.172 10301 1 1

pause