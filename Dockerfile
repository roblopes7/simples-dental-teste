FROM amazoncorretto:17

ARG JAR_FILE=target/simples-dental.jar

COPY ${JAR_FILE} application.jar

CMD apt-get update -y

COPY wait-for-it.sh /usr/local/bin/

ENTRYPOINT ["wait-for-it.sh", "db:5432", "--", "java", "-Xmx2048M", "-jar", "/application.jar"]
