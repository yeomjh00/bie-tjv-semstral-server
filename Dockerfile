FROM ubuntu:latest
LABEL authors="duawo"
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
ENTRYPOINT ["top", "-b"]

CMD ["java", "-version"]
EXPOSE 8080