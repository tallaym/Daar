CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE 
    IF NOT EXISTS document_types (
    id            UUID PRIMARY KEY  DEFAULT gen_random_uuid (),
    name          VARCHAR(30) UNIQUE NOT NULL,       -- ex: 'CNI', 'Facture', 'Contrat signé'
    description   VARCHAR(255),

    created_by UUID NOT NULL,
    updated_by UUID DEFAULT NULL,
    created_at TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT now() NOT NULL

);


CREATE TABLE documents (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    doc_name          VARCHAR(30) NOT NULL,              -- nom du fichier
    doc_format        VARCHAR(30) NOT NULL,         -- ex: application/pdf, image/jpeg

    storage_type      VARCHAR(30) NOT NULL CHECK (storage_type IN ('local', 'cloud')), 
    storage_path      VARCHAR(255) NOT NULL,      -- chemin sur le serveur local ou URL vers cloud / stockage objet

    is_sensitive      BOOLEAN DEFAULT false,  -- flag sécurité / confidentialité

    created_by      UUID NOT NULL,
    updated_by      UUID DEFAULT NULL,
    created_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at      TIMESTAMPTZ DEFAULT now() NOT NULL
    type_id         UUID NOT NULL REFERENCES document_types(id) ON DELETE CASCADE

        );

CREATE TABLE IF NOT EXISTS document_relations (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid (),

    document_id     UUID NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    object_type     VARCHAR(30) NOT NULL,    -- ex: 'contract', 'payment', 'user'
    object_id       UUID NOT NULL,      -- l’id de l’objet lié
    description     VARCHAR(255),           -- facultatif (ex: 'recto', 'signature', 'justificatif')
     
    created_at TIMESTAMPTZ DEFAULT now () NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT now () NOT NULL,
    deleted_at TIMESTAMPTZ,
    created_by UUID NOT NULL,
    updated_by UUID DEFAULT NULL
);


-- Fonction générique
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  IF ROW(NEW.*) IS DISTINCT FROM ROW(OLD.*) THEN
    NEW.updated_at = now();
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_updated_at_documents
BEFORE UPDATE ON documents
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at_document_relations
BEFORE UPDATE ON document_relations
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();



CREATE OR REPLACE FUNCTION prevent_update_if_deleted()
RETURNS TRIGGER AS $$
BEGIN
  IF OLD.deleted_at IS NOT NULL THEN
    RAISE EXCEPTION 'Cannot update a soft-deleted record';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_update_on_deleted_docs
BEFORE UPDATE ON documents
FOR EACH ROW
EXECUTE FUNCTION prevent_update_if_deleted();

CREATE TRIGGER trg_prevent_update_on_deleted_docs
BEFORE UPDATE ON document_relations
FOR EACH ROW
EXECUTE FUNCTION prevent_update_if_deleted();

