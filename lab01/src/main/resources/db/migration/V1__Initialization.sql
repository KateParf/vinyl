CREATE TYPE "condition_enum" AS ENUM (
  'bad',
  'good',
  'new'
);

CREATE TYPE "performer_enum" AS ENUM (
  'person',
  'group'
);

CREATE TABLE "record" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "year" integer,
  "publisher" varchar,
  "barcode" varchar,
  "genre" integer NOT NULL
);

CREATE TABLE "personal_record" (
  "id" integer PRIMARY KEY,
  "record_id" integer NOT NULL,
  "user_id" integer NOT NULL,
  "condition" condition_enum,
  "comment" varchar
);

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "login" varchar,
  "password" varchar,
  "email" varchar
);

CREATE TABLE "cover" (
  "id" integer PRIMARY KEY,
  "record_id" integer NOT NULL,
  "pic_filename" varchar
);

CREATE TABLE "genre" (
  "id" integer PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "performer" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "sign" performer_enum,
  "pic_filename" varchar
);

CREATE TABLE "performer_record" (
  "record_id" integer NOT NULL,
  "performer_id" integer NOT NULL
);

CREATE TABLE "group_performer" (
  "performer_id" integer NOT NULL,
  "group_id" integer NOT NULL
);

CREATE TABLE "tracks" (
  "id" integer PRIMARY KEY,
  "record_id" integer NOT NULL,
  "name" varchar
);

ALTER TABLE "record" ADD FOREIGN KEY ("genre") REFERENCES "genre" ("id");

ALTER TABLE "personal_record" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id");

ALTER TABLE "personal_record" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "cover" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id");

ALTER TABLE "performer_record" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id");

ALTER TABLE "performer_record" ADD FOREIGN KEY ("performer_id") REFERENCES "performer" ("id");

ALTER TABLE "group_performer" ADD FOREIGN KEY ("performer_id") REFERENCES "performer" ("id");

ALTER TABLE "group_performer" ADD FOREIGN KEY ("group_id") REFERENCES "performer" ("id");

ALTER TABLE "tracks" ADD FOREIGN KEY ("record_id") REFERENCES "record" ("id");
