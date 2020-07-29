@echo off

set CLASSPATH=.;..\libs\hsqldb.jar;%CLASSPATH%

@echo on

java org.hsqldb.util.DatabaseManagerSwing -url "jdbc:hsqldb:RR"

pause