FROM debian:10.1-slim

# Loom JDK 14
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN curl -s https://download.java.net/java/early_access/loom/2/openjdk-14-loom+2-4_linux-x64_bin.tar.gz | tar xvz -C /

ENV JAVA_HOME /jdk-14
ENV PATH $JAVA_HOME/bin:$PATH

# Setup gradle wrapper
WORKDIR /src
COPY gradle /src/gradle
COPY gradlew /src/gradlew
RUN ./gradlew --version

COPY build.gradle.kts /src/
COPY settings.gradle.kts /src/
RUN ./gradlew --no-daemon dependencies

COPY . /src
RUN ./gradlew --no-daemon --info compileJava

CMD exec ./gradlew --no-daemon --info run