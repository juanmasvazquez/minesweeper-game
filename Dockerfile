FROM openjdk:8-jdk-alpine
RUN apk add ttf-dejavu
ARG JVM_MEMORY
ARG JMX_PORTS
RUN mkdir -p /tmp/logs
VOLUME /tmp
ADD target/minesweeper-game-0.0.1.jar app.jar
ENV JAVA_OPTS=$JVM_MEMORY
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -jar /app.jar" ]