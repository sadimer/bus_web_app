DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "tickets" CASCADE;
DROP TABLE IF EXISTS "subroutes" CASCADE;
DROP TABLE IF EXISTS "routes" CASCADE;
DROP TABLE IF EXISTS "company" CASCADE;
DROP TABLE IF EXISTS "stations_of_route" CASCADE;
DROP TABLE IF EXISTS "stations" CASCADE;

CREATE TABLE "users" (
    user_id SERIAL PRIMARY KEY,
    user_name TEXT NOT NULL,
    user_email TEXT,
    user_phone TEXT,
    user_passwd TEXT NOT NULL,
    is_admin BOOLEAN NOT NULL,
    user_login TEXT NOT NULL UNIQUE
);

CREATE TABLE "company" (
    company_id SERIAL PRIMARY KEY,
    company_name TEXT NOT NULL
);

CREATE TABLE "routes" (
    route_id SERIAL PRIMARY KEY,
    route_name TEXT,
    company_id INTEGER REFERENCES "company"
);

CREATE TABLE "stations" (
    st_id SERIAL PRIMARY KEY,
    st_name TEXT NOT NULL,
    city TEXT NOT NULL
);

CREATE TABLE "subroutes" (
    sub_id SERIAL PRIMARY KEY,
    arrival_st_id INTEGER REFERENCES "stations" NOT NULL,
    depart_st_id INTEGER REFERENCES "stations" NOT NULL,
    route_id INTEGER REFERENCES "routes" NOT NULL
);

CREATE TABLE "tickets" (
    ticket_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "users",
    sub_id INTEGER REFERENCES "subroutes" NOT NULL,
    seats INTEGER NOT NULL,
    price_rub NUMERIC NOT NULL
);

CREATE TABLE "stations_of_route" (
    strt_id SERIAL PRIMARY KEY,
    route_id INTEGER REFERENCES "routes" NOT NULL,
    st_id INTEGER REFERENCES "stations" NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    depart_time TIMESTAMP NOT NULL,
    st_index INTEGER NOT NULL
);
