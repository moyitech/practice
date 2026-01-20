#!/usr/bin/env bash
set -euo pipefail

pids=$(pgrep -f "node.*(vue-cli-service|webpack-dev-server).*serve" || true)
if [[ -z "$pids" ]]; then
  echo "No running frontend process found."
  exit 0
fi

echo "Stopping frontend (PID: $pids)..."
kill $pids
