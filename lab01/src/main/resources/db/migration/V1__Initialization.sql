CREATE TYPE "condition_enum" AS ENUM (
  'BAD',
  'GOOD',
  'NEW'
);

CREATE TYPE "role_enum" AS ENUM (
  'DEFAULT',
  'ADMIN'
);

CREATE TABLE "record" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "year" integer,
  "publisher" varchar,
  "barcode" varchar UNIQUE,
  "genre_id" integer NOT NULL
);

CREATE TABLE "personal_record" (
  "id" SERIAL PRIMARY KEY,
  "record_id" integer NOT NULL,
  "user_id" integer NOT NULL,
  "condition" condition_enum,
  "comment" varchar
);

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "login" varchar UNIQUE,
  "password" varchar,
  "email" varchar UNIQUE,
  "user_role" role_enum
);

CREATE TABLE "cover" (
  "id" SERIAL PRIMARY KEY,
  "record_id" integer NOT NULL,
  "picture" varchar UNIQUE
);

CREATE TABLE "genre" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar UNIQUE
);

CREATE TABLE "performer_record" (
  "record_id" integer NOT NULL,
  "performer_id" integer,
  "group_id" integer
);

CREATE TABLE "tracks" (
  "id" SERIAL PRIMARY KEY,
  "record_id" integer NOT NULL,
  "name" varchar
);

CREATE TABLE "performer" (
  "id" SERIAL PRIMARY KEY,
  "group_id" integer,
  "name" varchar UNIQUE,
  "picture" varchar
);

CREATE TABLE "groups" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar UNIQUE,
  "picture" varchar
);

ALTER TABLE "record" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "personal_record" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "personal_record" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "cover" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "performer_record" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "performer_record" ADD FOREIGN KEY ("performer_id") REFERENCES "performer" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "performer_record" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "tracks" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id") ON UPDATE CASCADE;

ALTER TABLE "performer" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id") ON UPDATE CASCADE;

