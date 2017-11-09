md classes
javac -d classes -classpath tdbapi.jar demo\tdbapi\*.java

cd classes
jar -cvf ..\demo.jar tdbapi

pause