CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE
    IF NOT EXISTS payments_mode (
        id              UUID PRIMARY KEY DEFAULT gen_random_uuid (),

        nature          VARCHAR(30) NOT NULL,
        operator        VARCHAR(30),
        transaction_id  VARCHAR(30),

        created_at      TIMESTAMPTZ DEFAULT now () NOT NULL,
        updated_at      TIMESTAMPTZ DEFAULT now () NOT NULL,
        deleted_at      TIMESTAMPTZ,
        created_by      UUID NOT NULL,
        updated_by      UUID DEFAULT NULL
    );

CREATE TABLE
    IF NOT EXISTS payments (
        id              UUID PRIMARY KEY DEFAULT gen_random_uuid (),

        amount          NUMERIC(12, 2) NOT NULL,
        fee_id          UUID NOT NULL,
        type_id         UUID NOT NULL REFERENCES payments_mode (id) ON DELETE CASCADE,

        created_at      TIMESTAMPTZ DEFAULT now () NOT NULL,
        created_by      UUID NOT NULL,
        updated_by      UUID DEFAULT NULL
    );