#!/usr/bin/env bash
if [ -z "$1" ]
    then
        souce_tests=./docs/image-tests.yml
    else
        source_tests=$1
fi
mvn -P github package
version=1.1.0
app=docker-unittests-${version}.jar
shade_app=docker-unittests-app-${version}.jar
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=10050"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockCommercialFeatures"
JAVA_OPTS="${JAVA_OPTS} -XX:+FlightRecorder"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockDiagnosticVMOptions"
JAVA_OPTS="${JAVA_OPTS} -XX:+DebugNonSafepoints"
sleep 2
set -e
unzip -l target/${app}
unzip -l target/${shade_app} | grep "App.class\|MANIFEST.MF"
java ${JAVA_OPTS} -jar target/${shade_app} \
    -f ./docs/image-tests.yml \
    -i openjdk:9.0.1-11
