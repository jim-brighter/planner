#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 -U postgres <<-EOSQL
    SELECT pg_terminate_backend(pg_stat_activity.pid)
        FROM pg_stat_activity
        WHERE pg_stat_activity.datname = 'eventsdb';
    DROP DATABASE IF EXISTS eventsdb;
    CREATE DATABASE EVENTSDB OWNER postgres;
EOSQL