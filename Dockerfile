FROM openjdk:13-alpine3.10 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN /opt/openjdk-13/bin/java -Djarmode=layertools -jar application.jar extract

FROM openjdk:13-alpine3.10

ENV APP_ROOT /app
ENV CONFIG_DIR $APP_ROOT/config
ARG GIT_COMMIT=latest

LABEL "GIT_COMMIT"="$GIT_COMMIT"
LABEL "MAINTAINER"="Kamran Ghiasvand <kamran.ghaisvand@gmail.com>"

RUN addgroup -S bluebox && adduser -S authuser -G bluebox

RUN mkdir $APP_ROOT
RUN chown authuser:bluebox ${APP_ROOT}

USER authuser:bluebox
RUN mkdir $CONFIG_DIR
COPY --chown=authuser:bluebox --from=builder /application/dependencies/ /app/
COPY --chown=authuser:bluebox --from=builder /application/spring-boot-loader/ /app/
COPY --chown=authuser:bluebox --from=builder /application/snapshot-dependencies/ /app/
COPY --chown=authuser:bluebox --from=builder /application/application/ /app/

USER root
COPY entrypoint.sh /app/entrypoint.sh
RUN chown authuser:bluebox /app/entrypoint.sh && \
     chmod u+x /app/entrypoint.sh && \
     dos2unix /app/entrypoint.sh

USER authuser
WORKDIR /app
ENTRYPOINT ["./entrypoint"]