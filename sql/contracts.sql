CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE
    IF NOT EXISTS fee_typeS (
        id                  UUID PRIMARY KEY DEFAULT gen_random_uuid (),

        code_fee            VARCHAR(20) UNIQUE NOT NULL,
        description         VARCHAR(255),
        active              boolean DEFAULT TRUE,

        created_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        updated_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        deleted_at          TIMESTAMPTZ,
        created_by          UUID NOT NULL,
        updated_by          UUID DEFAULT NULL
    );

CREATE TABLE
    IF NOT EXISTS contract_typeS (
        id                  UUID PRIMARY KEY DEFAULT gen_random_uuid (),

        code_contract       VARCHAR(20) UNIQUE NOT NULL,
        description         VARCHAR(255),
        active              boolean DEFAULT TRUE,

        created_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        updated_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        deleted_at          TIMESTAMPTZ,
        created_by          UUID NOT NULL,
        updated_by          UUID DEFAULT NULL
    );

CREATE TABLE
    IF NOT EXISTS contracts (
        id                  UUID PRIMARY KEY DEFAULT gen_random_uuid (),
        start_date          DATE NOT NULL,
        end_date            DATE NOT NULL,

        signed_at           TIMESTAMPTZ,
        seller_id           UUID,

        type_id             UUID NOT NULL REFERENCES contract_types (id) ON DELETE CASCADE, -- 'rental', 'sale', etc.
        user_id             UUID NOT NULL, -- ID de l’acheteur / locataire (venant de l’API users/persons)               
        estate_id           UUID NOT NULL, -- Référence au bien (API properties)
        
        created_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        updated_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        deleted_at          TIMESTAMPTZ,
        created_by          UUID NOT NULL,
        updated_by          UUID DEFAULT NULL,

        CONSTRAINT chk_dates CHECK (start_date <= end_date)
    );

CREATE TABLE
    IF NOT EXISTS contract_fees (
        id                  UUID PRIMARY KEY DEFAULT gen_random_uuid (),

        amount              NUMERIC(12, 2) NOT NULL CHECK(amount>=0),
        due_date            DATE NOT NULL,
        frequency           VARCHAR(10) NOT NULL DEFAULT 'once', -- 'once', 'monthly', etc.
        status              VARCHAR(30) DEFAULT 'pending', -- 'paid', 'cancelled', etc.
        description         VARCHAR(255) -- 'deposit', 'installment', 'fee', etc.
        contract_id         UUID NOT NULL REFERENCES contracts (id) ON DELETE CASCADE,
        type_id             UUID NOT NULL REFERENCES fee_types (id) ON DELETE CASCADE, -- "Caution", "1ère tranche", "Frais de gestion"
        
        created_at          TIMESTAMPTZ DEFAULT now () NOT NULL,
        updated_at          TIMESTAMPTZ DEFAULT NULL,
        deleted_at          TIMESTAMPTZ DEFAULT NULL,
        created_by          UUID NOT NULL,
        updated_by          UUID DEFAULT NULL
    );


CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_update_contracts
BEFORE UPDATE ON contracts
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_update_contract_fees
BEFORE UPDATE ON contract_fees
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_update_fees_type
BEFORE UPDATE ON fee_types
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_update_contracts_type
BEFORE UPDATE ON contract_types
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();



-- Supposons une table payments(fee_id UUID, amount NUMERIC, paid_at TIMESTAMPTZ...)

CREATE OR REPLACE FUNCTION mark_fee_paid()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE contract_fees
  SET status = 'paid',
      updated_at = now()
  WHERE id = NEW.fee_id;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_mark_fee_paid
AFTER INSERT ON payments
FOR EACH ROW
EXECUTE FUNCTION mark_fee_paid();

CREATE OR REPLACE FUNCTION check_due_date_future()
RETURNS trigger AS $$
BEGIN
  IF NEW.due_date <= CURRENT_DATE THEN
    RAISE EXCEPTION format('La date d''échéance (%s) doit être dans le futur.', NEW.due_date);
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_due_date_future
BEFORE INSERT OR UPDATE ON payments
FOR EACH ROW
EXECUTE FUNCTION check_due_date_future();
