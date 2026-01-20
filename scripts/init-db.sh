#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SQL_FILE="$ROOT_DIR/backend/db/init.sql"
MYSQL_CONTAINER=${MYSQL_CONTAINER:-carbon-mysql}
MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-13579MXZmxz...}

if [[ ! -f "$SQL_FILE" ]]; then
  echo "SQL file not found: $SQL_FILE" >&2
  exit 1
fi

echo "Initializing database in container: $MYSQL_CONTAINER"
docker exec -i "$MYSQL_CONTAINER" mysql -uroot -p"$MYSQL_ROOT_PASSWORD" < "$SQL_FILE"

echo "Done."
