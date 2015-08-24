IF "%WORKING_SET%"=="" SET WORKING_SET=trunk

SET COGIVUI_HOME=d:\Projects\cogivui
SET JAVA_HOME=%COGIVUI_HOME%\tools\jdk1.7.0_21
SET MAVEN_HOME=%COGIVUI_HOME%\tools\apache-maven-3.0.5
SET M2_REPOSITORY=%COGIVUI_HOME%\m2repository
SET CATALINA_HOME=%COGIVUI_HOME%\tools\apache-tomcat-7.0.27
SET SETTING_VN=%COGIVUI_HOME%\etc\m2\settings_VN.xml
SET PATH=%PATH%;%COGIVUI_HOME%;%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%CATALINA_HOME%\bin
SET MAVEN_OPTS=-Xdebug -Xnoagent -Xms256M -Xmx512M -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n
SET CATALINA_HOME=%COGIVUI_HOME%\tools\apache-tomcat-7.0.27

DOSKEY tomcat=call %CATALINA_HOME%\bin\startup.bat
DOSKEY mi=mvn clean install -DskipTests
DOSKEY me=mvn eclipse:clean eclipse:eclipse -Dwtpversion=2.0
DOSKEY ms=mvn dependency:sources

CMD