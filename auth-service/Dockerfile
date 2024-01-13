FROM eclipse-temurin:17-jre-alpine

ARG SERVICE_NAME

ENV SERVICE_NAME_ENV=${SERVICE_NAME}

WORKDIR /service

COPY target/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar /service

RUN apk update && apk add curl

ENTRYPOINT java -jar ${SERVICE_NAME_ENV}-0.0.1-SNAPSHOT.jar