package com.daar.core.domain.port_out.document;


import com.daar.core.domain.model.document.TypeDocument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeDocumentRepository {

    TypeDocument insert(TypeDocument type);
    TypeDocument update(TypeDocument type);
    Optional<TypeDocument> findById(UUID id);
    void deleteById(UUID id);
    List<TypeDocument> findAll();
}
