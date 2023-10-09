CREATE extension if NOT exists "uuid-ossp";

CREATE TABLE IF NOT EXISTS geo_features
(
    id UUID UNIQUE NOT NULL default uuid_generate_v1() primary key,
    country_name VARCHAR(50) UNIQUE NOT NULL,
    country_code VARCHAR(3) UNIQUE NOT NULL,
    coordinates JSONB              NOT NULL
    );

ALTER TABLE geo_features
    OWNER TO postgres;

DELETE FROM geo_features
