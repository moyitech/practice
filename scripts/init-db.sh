#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SQL_FILE="$ROOT_DIR/backend/db/init.sql"
MYSQL_CONTAINER=${MYSQL_CONTAINER:-carbon-mysql}
MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-root}
MYSQL_ROOT_PASSWORD_TARGET=${MYSQL_ROOT_PASSWORD_TARGET:-13579MXZmxz...}

if [[ ! -f "$SQL_FILE" ]]; then
  echo "SQL file not found: $SQL_FILE" >&2
  exit 1
fi

echo "Initializing database in container: $MYSQL_CONTAINER"

mysql_ping() {
  local password="$1"
  docker exec -i "$MYSQL_CONTAINER" mysql -uroot -p"$password" -e "SELECT 1" >/dev/null 2>&1
}

CURRENT_ROOT_PASSWORD="$MYSQL_ROOT_PASSWORD"
if ! mysql_ping "$CURRENT_ROOT_PASSWORD"; then
  if mysql_ping "$MYSQL_ROOT_PASSWORD_TARGET"; then
    CURRENT_ROOT_PASSWORD="$MYSQL_ROOT_PASSWORD_TARGET"
  else
    echo "Unable to connect to MySQL with provided root passwords." >&2
    exit 1
  fi
fi

if [[ "$CURRENT_ROOT_PASSWORD" != "$MYSQL_ROOT_PASSWORD_TARGET" ]]; then
  echo "Updating MySQL root password"
  docker exec -i "$MYSQL_CONTAINER" mysql -uroot -p"$CURRENT_ROOT_PASSWORD" \
    -e "ALTER USER IF EXISTS 'root'@'%' IDENTIFIED BY '$MYSQL_ROOT_PASSWORD_TARGET'; \
        ALTER USER IF EXISTS 'root'@'localhost' IDENTIFIED BY '$MYSQL_ROOT_PASSWORD_TARGET'; \
        FLUSH PRIVILEGES;"
  CURRENT_ROOT_PASSWORD="$MYSQL_ROOT_PASSWORD_TARGET"
fi

docker exec -i "$MYSQL_CONTAINER" mysql -uroot -p"$CURRENT_ROOT_PASSWORD" < "$SQL_FILE"

echo "Done."
