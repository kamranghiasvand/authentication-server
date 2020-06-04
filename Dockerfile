FROM openjdk:13-alpine3.10
LABEL "MAINTAINER"="Kamran Ghiasvand <kamran.ghaisvand@gmail.com"

RUN addgroup -S bluebox && adduser -S authuser -G bluebox
USER authuser:bluebox

COPY --chown=authuser:bluebox target/dependency/BOOT-INF/lib /app/lib
COPY --chown=authuser:bluebox target/dependency/META-INF /app/META-INF
COPY --chown=authuser:bluebox target/dependency/BOOT-INF/classes /app

USER root
COPY entrypoint.sh /app/entrypoint.sh
RUN chown authuser:bluebox /app/entrypoint.sh && \
     chmod u+x /app/entrypoint.sh && \
     dos2unix /app/entrypoint.sh

# ENV LOG_LEVEL=warn
# ENV SERVER_PORT=8081
# ENV LOG_REQ_RESP=false
# ENV MYSQL_IP=localhost
# ENV MYSQL_PORT=3306
# ENV MYSQL_USER=root
# ENV MYSQL_PASS=123456
# ENV MYSQL_DB_NAME=auth_server_prod

USER authuser
ENTRYPOINT ["/app/entrypoint.sh"]