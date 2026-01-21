#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MAVEN_CMD=${MAVEN_CMD:-mvn}
OUTPUT_DIR=${OUTPUT_DIR:-}
SKIP_TESTS=${SKIP_TESTS:-}

if [[ -x /usr/libexec/java_home ]]; then
  JAVA_HOME_21="$(/usr/libexec/java_home -v 21 2>/dev/null || true)"
  if [[ -n "$JAVA_HOME_21" ]]; then
    export JAVA_HOME="$JAVA_HOME_21"
    export PATH="$JAVA_HOME/bin:$PATH"
  fi
fi

if ! command -v "$MAVEN_CMD" >/dev/null 2>&1; then
  echo "mvn not found in PATH. Set MAVEN_CMD or install Maven." >&2
  exit 1
fi

find_latest_jar() {
  local dir="$1"
  local jar=""
  local candidate

  shopt -s nullglob
  for candidate in "$dir"/target/*.jar; do
    case "$candidate" in
      *-sources.jar|*-javadoc.jar) continue ;;
    esac
    if [[ -z "$jar" || "$candidate" -nt "$jar" ]]; then
      jar="$candidate"
    fi
  done
  shopt -u nullglob

  if [[ -z "$jar" ]]; then
    echo "Jar not found in $dir/target after build." >&2
    exit 1
  fi

  echo "$jar"
}

build_service() {
  local name="$1"
  local dir="$2"
  local jar

  echo "Building $name..."
  if [[ "$SKIP_TESTS" == "1" || "$SKIP_TESTS" == "true" ]]; then
    (cd "$dir" && "$MAVEN_CMD" clean package -DskipTests)
  else
    (cd "$dir" && "$MAVEN_CMD" clean package)
  fi

  jar="$(find_latest_jar "$dir")"
  echo "$name jar: $jar"

  if [[ -n "$OUTPUT_DIR" ]]; then
    mkdir -p "$OUTPUT_DIR"
    cp -f "$jar" "$OUTPUT_DIR/"
  fi
}

build_service "carbon-auth" "$ROOT_DIR/backend/carbon-auth"
build_service "carbon-system" "$ROOT_DIR/backend/carbon-system"
build_service "carbon-assets" "$ROOT_DIR/backend/carbon-assets"

if [[ -n "$OUTPUT_DIR" ]]; then
  echo "Copied jars to $OUTPUT_DIR"
fi
