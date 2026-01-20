#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_DIR="$ROOT_DIR/logs"
JAVA_HOME_21="$(/usr/libexec/java_home -v 21)"

mkdir -p "$LOG_DIR"

export JAVA_HOME="$JAVA_HOME_21"
export PATH="$JAVA_HOME/bin:$PATH"

start_service() {
  local name="$1"
  local dir="$2"
  local log_file="$LOG_DIR/${name}.log"

  echo "Starting $name..."
  (cd "$dir" && nohup mvn spring-boot:run -Dspring-boot.run.fork=true >"$log_file" 2>&1 &)
}

start_service "carbon-auth" "$ROOT_DIR/backend/carbon-auth"
start_service "carbon-system" "$ROOT_DIR/backend/carbon-system"
start_service "carbon-assets" "$ROOT_DIR/backend/carbon-assets"

echo "Done. Logs: $LOG_DIR"
