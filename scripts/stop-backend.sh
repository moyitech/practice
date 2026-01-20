#!/usr/bin/env bash
set -euo pipefail

stop_service() {
  local name="$1"
  local pattern="$2"

  local pids
  pids=$(pgrep -f "$pattern" || true)
  if [[ -z "$pids" ]]; then
    echo "No running process found for $name."
    return 0
  fi

  echo "Stopping $name (PID: $pids)..."
  kill $pids
}

stop_service "carbon-auth" "com.carbon.auth.CarbonAuthApplication"
stop_service "carbon-system" "com.carbon.system.CarbonSystemApplication"
stop_service "carbon-assets" "com.carbon.assets.CarbonAssetsApplication"
