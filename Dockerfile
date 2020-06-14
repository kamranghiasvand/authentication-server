### Build Project ###
FROM maven:3.6.3-jdk-11 as builder
# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
#Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
COPY src /build/src
# Build application
RUN mvn package -Dmaven.test.skip=true -P production
COPY target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

### Run Application ###
FROM openjdk:13-alpine3.10 as runtime

ENV GIT_COMMIT=latest
LABEL "GIT_COMMIT"="$GIT_COMMIT"
LABEL "MAINTAINER"="Kamran Ghiasvand <kamran.ghaisvand@gmail.com>"

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
ENV JAVA_OPT ""
ENV APP_ROOT /app
ENV CONFIG_DIR $APP_ROOT/config
ENV LOGGING_LEVEL warn
ENV SERVER_PORT 8081

RUN mkdir $APP_ROOT
RUN mkdir $CONFIG_DIR
RUN addgroup -S bluebox && adduser -S authuser -G bluebox
RUN chown authuser:bluebox ${APP_ROOT}

USER authuser:bluebox
COPY --chown=authuser:bluebox --from=builder /build/dependencies/ /app/
COPY --chown=authuser:bluebox --from=builder /build/spring-boot-loader/ /app/
COPY --chown=authuser:bluebox --from=builder /build/snapshot-dependencies/ /app/
COPY --chown=authuser:bluebox --from=builder /build/application/ /app/


USER root
COPY entrypoint.sh /app/entrypoint.sh
RUN chown authuser:bluebox /app/entrypoint.sh && \
     chmod u+x /app/entrypoint.sh && \
     dos2unix /app/entrypoint.sh

USER authuser
WORKDIR /app
ENTRYPOINT ["./entrypoint.sh"]