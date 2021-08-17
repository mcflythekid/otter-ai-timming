:: JDK8 is required
set JAVA_HOME=%JDK8%
set DESTINATION="C:/App/sub/sub.jar"

set SOURCE=target\sub-1.0-SNAPSHOT-jar-with-dependencies.jar
call mvn clean install || echo BUILD_ERROR && exit /b

java -Xmx2G -jar build.jar --jarIn %SOURCE% --jarOut obfuscated.jar || echo OBFUSCATE_ERROR && exit /b
copy /b/v/y obfuscated.jar %DESTINATION%
del /F /Q obfuscated.jar
