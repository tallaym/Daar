create database estates
	with owner = postgres
	connection limit = -1;

\c estates

CREATE EXTENSION IF NOT EXISTS "pgcrypto";  -- Pour gen_random_uuid()

CREATE TABLE IF NOT EXISTS address (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 
    lot             VARCHAR(10),       -- plot or lot number
    road            VARCHAR(30),       -- street name
    description     VARCHAR(255),
    suburb          VARCHAR(50),              -- full address text (optional)
    postal_code     VARCHAR(10),
    town            VARCHAR(50) NOT NULL,
    state           VARCHAR(50),
    country         VARCHAR(50) NOT NULL,
    longitude       NUMERIC(9,6) DEFAULT NULL,      -- optional geolocation
    latitude        NUMERIC(9,6) DEFAULT NULL,

    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT NULL,
    deleted_at      TIMESTAMPTZ DEFAULT NULL,

    created_by      UUID NOT NULL,     -- reference to persons in another API
    updated_by      UUID DEFAULT NULL,
    deleted_by      UUID DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS estates (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Informations générales    
    estate_type     VARCHAR(30) NOT NULL,       -- ex : "villa", "studio", "maison mitoyenne", etc.
    area            NUMERIC(7,2) ,               -- m² 
    year_built      INTEGER CHECK (year_built >= 1700 AND year_built <= EXTRACT(YEAR FROM now())),
    nb_floors       INTEGER CHECK(nb_floors>=0) NOT NULL,
    
    -- Métadonnées
    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT NULL,
    deleted_at      TIMESTAMPTZ DEFAULT NULL,

    created_by      UUID NOT NULL,
    updated_by      UUID DEFAULT NULL,
    deleted_by      UUID DEFAULT NULL,

    owner_id        UUID NOT NULL,
    address_id      UUID NOT NULL REFERENCES address(id) ON DELETE CASCADE
    
);


CREATE TABLE IF NOT EXISTS rooms (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    floor           INTEGER CHECK(floor>=0) NOT NULL,
    number          INTEGER CHECK(nb>=0) NOT NULL,
    area            NUMERIC(7,2),
    
    created_at		TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at     	TIMESTAMPTZ DEFAULT NULL,
    deleted_at     	TIMESTAMPTZ DEFAULT NULL,

    created_by 		UUID NOT NULL,
    updated_by 		UUID DEFAULT NULL,

    estate_id       UUID REFERENCES estates(id) NOT NULL ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS eqt (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    category        VARCHAR(50) NOT NULL,                 -- Exemple : "Électrique", "Sanitaire"
    brand           VARCHAR(50),                    -- Marque de l'équipement
    model           VARCHAR(50),
    purchase_date   DATE,
    value           NUMERIC(10,2),                  -- Valeur estimée
    
    created_at		TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at     	TIMESTAMPTZ DEFAULT NULL,
    deleted_at     	TIMESTAMPTZ DEFAULT NULL,

    created_by 		UUID NOT NULL,
    updated_by 		UUID,

    room_id        UUID REFERENCES rooms(id) NOT NULL ON DELETE CASCADE
);

-- Historique des états
CREATE TABLE IF NOT EXISTS status (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    target_type     VARCHAR(20) NOT NULL,   -- 'property', 'room', etc.
    target_id       UUID NOT NULL,
    condition       VARCHAR(20),
    availability    VARCHAR(20),

    created_at		TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT NULL ,
    created_by 		UUID NOT NULL,
    updated_by 		UUID DEFAULT NULL,

    UNIQUE (target_type, target_id) 
);


CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger pour table address
CREATE TRIGGER trg_update_address
BEFORE UPDATE ON address
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour table estates
CREATE TRIGGER trg_update_estates
BEFORE UPDATE ON estates
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour table rooms
CREATE TRIGGER trg_update_rooms
BEFORE UPDATE ON rooms
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour table eqt
CREATE TRIGGER trg_update_eqt
BEFORE UPDATE ON eqt
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour table status
CREATE TRIGGER trg_update_status
BEFORE UPDATE ON status
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();





