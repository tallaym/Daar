CREATE EXTENSION IF NOT EXISTS "pgcrypto";  -- Pour gen_random_uuid()

CREATE TABLE IF NOT EXISTS flows (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id         UUID NOT NULL,
    flow_type       VARCHAR(30) NOT NULL,         -- ex: 'login_password', 'magic_link', 'otp_verification'
    status          VARCHAR(30) NOT NULL,            -- ex: 'started', 'pending_otp', 'completed', 'failed'
    current_step    VARCHAR(30),               -- ex: 'password_entered', 'otp_sent', 'otp_verified'

    started_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ,    

    metadata JSONB                   -- données supplémentaires (ip, device, etc.)
);


CREATE TABLE IF NOT EXISTS logs (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    action          VARCHAR(50) NOT NULL,            -- ex: 'login_attempt', 'invoice_created', 'password_reset'
    resource        VARCHAR(50) NOT NULL,                  -- ex: 'invoice', 'user', 'session'
    resource_id     UUID NOT NULL,               -- ID de la ressource ciblée
    success         BOOLEAN DEFAULT true,
    details         JSONB,                 -- données complémentaires flexibles (ip, user_agent, message d’erreur...)
    
    user_id         UUID NOT NULL,
    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL
);


CREATE TABLE IF NOT EXISTS credentials (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id         UUID NOT NULL,
    type            VARCHAR(30) NOT NULL CHECK (type IN ('password', 'otp_seed', 'passkey', 'oauth')),
    identifier      VARCHAR(30) TEXT,        -- ex: email, phone number, OAuth provider user id
    secret          VARCHAR(255),            -- mot de passe hashé, clé OTP, null si pas applicable

    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ DEFAULT now() NOT NULL,

    UNIQUE (type, identifier)
);


CREATE TABLE IF NOT EXISTS sessions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id         UUID NOT NULL,

    token           VARCHAR(2048) NOT NULL,
    expires_at      TIMESTAMPTZ NOT NULL,
    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    revoked_at      TIMESTAMPTZ DEFAULT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,

    CONSTRAINT chk_valid_dates CHECK (expires_at > created_at)
);



CREATE TABLE IF NOT EXISTS devices (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id         UUID NOT NULL,
    device_name     TEXT,
    device_fingerprint TEXT,

    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    last_used       TIMESTAMPTZ DEFAULT now(),
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,

    UNIQUE (user_id, device_fingerprint)
);



CREATE TABLE IF NOT EXISTS roles (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    rolename       	VARCHAR(50) NOT NULL,
    description     VARCHAR(255),
        
    created_at		TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at     	TIMESTAMPTZ DEFAULT now() NOT NULL,
    deleted_at     	TIMESTAMPTZ DEFAULT NULL,

    created_by 		UUID NOT NULL,
    updated_by 		UUID
);

CREATE TABLE IF NOT EXISTS permissions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    permission_name       VARCHAR(50) NOT NULL,
    description     VARCHAR(255),
        
    created_at		TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at     	TIMESTAMPTZ DEFAULT now() NOT NULL,
    deleted_at     	TIMESTAMPTZ DEFAULT NULL,

    created_by 		UUID NOT NULL,
    updated_by 		UUID 
);

CREATE TABLE IF NOT EXISTS user_roles (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id         UUID NOT NULL,
    role_id         UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,

    created_by      UUID NOT NULL,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ,

    UNIQUE (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_permissions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id         UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id   UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    created_by      UUID NOT NULL,

    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ,

    UNIQUE (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS user_permissions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL ,
    permission_id   UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
   
    created_by      UUID NOT NULL,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ,

    UNIQUE (user_id, permission_id)
);


-- Création de la fonction
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER trg_update_role
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger sur table permission
CREATE TRIGGER trg_update_permission
BEFORE UPDATE ON permissions
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour personne_role
CREATE TRIGGER trg_update_personne_role
BEFORE UPDATE ON user_roles
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour role_permission
CREATE TRIGGER trg_update_role_permission
BEFORE UPDATE ON role_permissions
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Trigger pour personne_permission
CREATE TRIGGER trg_update_personne_permission
BEFORE UPDATE ON user_permissions
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Flows
CREATE TRIGGER trg_update_flows
BEFORE UPDATE ON flows
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Credentials
CREATE TRIGGER trg_update_credentials
BEFORE UPDATE ON credentials
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Sessions
CREATE TRIGGER trg_update_sessions
BEFORE UPDATE ON sessions
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Devices
CREATE TRIGGER trg_update_devices
BEFORE UPDATE ON devices
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE OR REPLACE FUNCTION update_last_used()
RETURNS TRIGGER AS $$
BEGIN
    NEW.last_used = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_last_used
BEFORE UPDATE ON devices
FOR EACH ROW
EXECUTE FUNCTION update_last_used();
