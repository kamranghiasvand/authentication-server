#authentication-server
An OAuth2 authentication and authorization Spring boot application based on Spring Security.
Expose REST api to communicate with. Use MySql as Database. Stateless application with capability to extent quickly in order to use in micro service architecture  

#Installation Guide
##Install git
1. Download and install https://git-scm.com/downloads
2. clone the project
## Install JAVA 11
1. Download and install JAVA 11 from https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
2. Set environment variable $JAVA_HOME to the path where Java has been installed 
## building project
1.Download and install maven from https://maven.apache.org/download.cgi
2. run `mvn clean package -Dmaven.test.skip=true`
## Creating Docker Image
1. Download and install Docker from https://docs.docker.com/
2. run `git log -1 --format=%h --abbrev=12 > _tag`
For Windows:
    run `FOR /F %A IN ('type _tag') DO docker build -t com.bluebox.security.auth-server:%~A --build-arg GIT_COMMIT=%~A .`
For linux :
    run `docker build -t com.bluebox.security.auth-server --build-arg GIT_COMMIT= .`