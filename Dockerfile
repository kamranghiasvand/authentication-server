FROM openjdk:13-alpine3.10
LABEL "MAINTAINER"="Kamran Ghiasvand <kamran.ghaisvand@gmail.com"

COPY ./target/dependency/BOOT-INF/lib /app/lib
COPY ./target/dependency/META-INF /app/META-INF
COPY ./target/dependency/BOOT-INF/classes /app

ENV LOG_LEVEL=warn
ENV SERVER_PORT=8081
ENV LOG_REQ_RESP=false
ENV MYSQL_IP=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_USER=root
ENV MYSQL_PASS=123456
ENV MYSQL_DB_NAME=auth_server_prod

ENTRYPOINT ["/opt/openjdk-13/bin/java","-cp","app:app/lib/*","com.bluebox.security.authenticationserver.AuthenticationServerApplication"]