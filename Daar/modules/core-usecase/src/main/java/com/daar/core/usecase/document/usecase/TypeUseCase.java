package com.daar.core.usecase.document.usecase;


import com.daar.core.domain.model.document.TypeDocument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeUseCase {

    TypeDocument create(TypeDocument t);
    TypeDocument update(TypeDocument type);
    void delete(UUID id);
    Optional<TypeDocument> findById(UUID id);
    List<TypeDocument> allTypes();
}
