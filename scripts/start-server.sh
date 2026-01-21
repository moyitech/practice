#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_DIR="$ROOT_DIR/logs"
JAVA_BIN=${JAVA_BIN:-java}
MAVEN_CMD=${MAVEN_CMD:-mvn}

mkdir -p "$LOG_DIR"

if ! command -v "$JAVA_BIN" >/dev/null 2>&1; then
  echo "java not found in PATH. Set JAVA_BIN or install Java 21." >&2
  exit 1
fi

find_jar() {
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
    echo "Jar not found in $dir/target. Building..."
    (cd "$dir" && "$MAVEN_CMD" clean package)

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
      echo "Jar still not found in $dir/target after build." >&2
      exit 1
    fi
  fi

  echo "$jar"
}

start_service() {
  local name="$1"
  local dir="$2"
  local log_file="$LOG_DIR/${name}.log"
  local jar

  jar="$(find_jar "$dir")"
  echo "Starting $name with $jar..."
  nohup "$JAVA_BIN" -jar "$jar" >"$log_file" 2>&1 &
}

start_service "carbon-auth" "$ROOT_DIR/backend/carbon-auth"
start_service "carbon-system" "$ROOT_DIR/backend/carbon-system"
start_service "carbon-assets" "$ROOT_DIR/backend/carbon-assets"

echo "Done. Logs: $LOG_DIR"
