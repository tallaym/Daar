CREATE DATABASE users
    WITH OWNER = postgres
    CONNECTION LIMIT = -1;

\c users

CREATE EXTENSION IF NOT EXISTS "pgcrypto";  -- Pour gen_random_uuid()

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    firstname        VARCHAR(50) NOT NULL,
    lastname         VARCHAR(50) NOT NULL,
    origin           VARCHAR(30) DEFAULT 'Senegalese',
    identity_type    VARCHAR(9) NOT NULL CHECK (identity_type IN ('CNI', 'PASSEPORT', 'PERMIS', 'SEJOUR')),
    identity_number  VARCHAR(50) UNIQUE NOT NULL CHECK (LENGTH(TRIM(identity_number)) >= 6 AND LENGTH(TRIM(identity_number)) <= 30)
,
    adress           VARCHAR(255),

    email            VARCHAR(128) UNIQUE CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    phone            VARCHAR(16) CHECK (phone ~ '^\+[1-9][0-9]{7,14}$'),

    created_at       TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at       TIMESTAMPTZ,
    suspended_until  TIMESTAMPTZ,

    created_by       UUID REFERENCES users(id) NOT NULL,   
    updated_by       UUID REFERENCES users(id),
    suspended_by     UUID REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTS user_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),


    profile_key     VARCHAR(30) NOT NULL,
    profile_value   VARCHAR(30),

    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,

    

    UNIQUE (user_id, profile_key)
);




-- Fonction générique update_timestamp
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers pour mise à jour automatique du champ updated_at
CREATE TRIGGER trg_update_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();


CREATE TRIGGER trg_update_profiles
BEFORE UPDATE ON user_profiles
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();


