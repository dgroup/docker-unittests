FROM docker:18-dind

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin
ENV JAVA_VERSION 8u151
ENV JAVA_ALPINE_VERSION 8.151.12-r0
ENV MAVEN_VERSION 3.5.2
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

RUN echo 'http://dl-cdn.alpinelinux.org/alpine/v3.2/main' >> /etc/apk/repositories && \
    apk update && \
    apk add --no-cache git=2.15.0-r1 && \
    apk add --no-cache openjdk8="$JAVA_ALPINE_VERSION" && \
    wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

CMD uname -a && echo "" && \
    git --version && echo "" && \
    docker -v && echo "" && \
    java -version && echo "" && \
    mvn -v