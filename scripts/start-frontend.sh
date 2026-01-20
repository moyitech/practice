#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_DIR="$ROOT_DIR/logs"
FRONTEND_DIR="$ROOT_DIR/frontend"
PORT=${PORT:-8080}

mkdir -p "$LOG_DIR"

cd "$FRONTEND_DIR"

if [[ ! -d node_modules ]]; then
  echo "Installing frontend dependencies..."
  npm install
fi

echo "Starting frontend on port $PORT..."
nohup env PORT="$PORT" npm run serve >"$LOG_DIR/frontend.log" 2>&1 &

echo "Done. Logs: $LOG_DIR/frontend.log"
if [[ "$PORT" == "80" ]]; then
  echo "Note: binding to port 80 may require sudo on macOS."
fi
