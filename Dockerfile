FROM openjdk:13-alpine3.10
LABEL "MAINTAINER"="Kamran Ghiasvand <kamran.ghaisvand@gmail.com"
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["/opt/openjdk-13/bin/java","-cp","app:app/lib/*","com.bluebox.security.authenticationserver.AuthenticationServerApplication"]