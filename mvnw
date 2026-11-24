#!/bin/sh
# Simple Maven wrapper.
# Uses Maven if it is installed; otherwise downloads Maven once into ~/.m2/wrapper.
set -e
MAVEN_VERSION=3.9.9
BASE_DIR=$(cd "$(dirname "$0")" && pwd)
cd "$BASE_DIR"

if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
fi

DIST_ROOT="$HOME/.m2/wrapper/dists"
MVN_HOME="$DIST_ROOT/apache-maven-$MAVEN_VERSION"

if [ ! -x "$MVN_HOME/bin/mvn" ]; then
  URL="https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"
  TMP=$(mktemp -d)
  echo "Downloading Maven $MAVEN_VERSION (one time only)..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$URL" -o "$TMP/maven.tar.gz"
  else
    wget -q "$URL" -O "$TMP/maven.tar.gz"
  fi
  mkdir -p "$DIST_ROOT"
  tar -xzf "$TMP/maven.tar.gz" -C "$DIST_ROOT"
  rm -rf "$TMP"
fi

exec "$MVN_HOME/bin/mvn" "$@"
