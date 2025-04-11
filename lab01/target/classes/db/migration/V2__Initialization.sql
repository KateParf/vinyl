CREATE TABLE "tokens" (
  "id" SERIAL PRIMARY KEY,
  "access_token" varchar UNIQUE,
  "refresh_token" varchar UNIQUE,
  "is_logged_out" boolean,
  "user_id" integer
);

ALTER TABLE "tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;