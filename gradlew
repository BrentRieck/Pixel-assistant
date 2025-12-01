#!/bin/sh
# Gradle start up script for UN*X

# Add default JVM options here
# Quotes around the options were causing them to be interpreted as a class name
# by the JVM in this environment, so we pass them directly instead.
DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'

APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=$(dirname "$PRG")"/$link"
    fi
done

SAVED="$(pwd)"
cd "$(dirname "$PRG")/" >/dev/null
APP_HOME="$(pwd -P)"
cd "$SAVED" >/dev/null

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

exec java $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
