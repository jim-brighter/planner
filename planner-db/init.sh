#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 -U postgres <<-EOSQL
    SELECT pg_terminate_backend(pg_stat_activity.pid)
        FROM pg_stat_activity
        WHERE pg_stat_activity.datname = 'eventsdb';
    DROP DATABASE IF EXISTS eventsdb;
    CREATE DATABASE EVENTSDB OWNER postgres;
EOSQL

psql -v ON_ERROR_STOP=1 -U postgres -d eventsdb <<-EOSQL
    CREATE SCHEMA IF NOT EXISTS events;

    CREATE TABLE events.events (
        event_id int8 NOT NULL,
        event_created_time timestamp NULL,
        event_description varchar(255) NULL,
        event_status varchar(255) NULL,
        event_type varchar(255) NULL,
        event_title varchar(255) NOT NULL,
        CONSTRAINT events_pkey PRIMARY KEY (event_id)
    )
    WITH (
        OIDS=FALSE
    ) ;

    CREATE TABLE events.images (
        image_id int8 NOT NULL,
        digital_ocean_key varchar(255) NOT NULL,
        fk_event int8 NULL,
        CONSTRAINT images_pkey PRIMARY KEY (image_id),
        CONSTRAINT images_event_fkey FOREIGN KEY (fk_event) REFERENCES events.events(event_id)
    )
    WITH (
        OIDS=FALSE
    ) ;

    CREATE TABLE events.comments (
        comment_id int8 NOT NULL,
        comment_text varchar(255) NOT NULL,
        comment_created_time timestamp NULL,
        CONSTRAINT comments_pkey PRIMARY KEY (comment_id)
    )
    WITH (
        OIDS=FALSE
    ) ;

    CREATE TABLE events.event_comments (
        event_comment_id int8 NOT NULL,
        fk_event int8 NOT NULL,
        comment varchar(255) NULL,
        CONSTRAINT event_comments_pkey PRIMARY KEY (event_comment_id),
        CONSTRAINT event_comments_event_fkey FOREIGN KEY (fk_event) REFERENCES events.events(event_id)
    )
    WITH (
        OIDS=FALSE
    ) ;

    CREATE SEQUENCE events.event_seq START 1 INCREMENT 50;
    CREATE SEQUENCE events.image_seq START 1 INCREMENT 50;
    CREATE SEQUENCE events.comment_seq START 1 INCREMENT 50;
    CREATE SEQUENCE events.event_comment_seq START 1 INCREMENT 50;

EOSQL