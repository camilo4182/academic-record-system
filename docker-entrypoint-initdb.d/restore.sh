#!/bin/bash

file="/docker-entrypoint-initdb.d/schema.sql"
dbname=academic-record-system

echo "Restoring DB using $file"
#pg_restore -U postgres --dbname=$dbname --verbose --single-transaction < "$file" || exit 1
psql $dbname < $file || exit 1