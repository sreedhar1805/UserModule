FROM tomcat:10.1.14-jre17-temurin
COPY UserModuleApp.war /usr/local/tomcat/webapps/
LABEL name="user_mod"
