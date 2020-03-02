FROM gradle:5.4.1-alpine as builder

USER root

WORKDIR /app

ADD build.gradle.kts /app
ADD settings.gradle.kts /app

COPY . /app
RUN gradle clean test build

FROM openjdk:13-slim-buster

WORKDIR /app

COPY docker/init.sh .
COPY --from=builder /app/build/libs/bank-account.jar /app/

ENTRYPOINT ["./init.sh"]
