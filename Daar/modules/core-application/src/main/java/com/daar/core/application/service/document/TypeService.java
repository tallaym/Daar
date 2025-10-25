package com.daar.core.application.service.document;

import com.daar.core.domain.model.document.TypeDocument;
import com.daar.core.port.in.usecase.document.TypeUseCase;
import com.daar.core.port.out.document.TypeDocumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TypeService implements TypeUseCase {

    private final TypeDocumentRepository tr;

    public TypeService(TypeDocumentRepository tr) {
        this.tr = tr;
    }

    @Override
    public TypeDocument create(TypeDocument t) {
        return tr.insert(t);
    }

    @Override
    public TypeDocument update(TypeDocument type) {
        return tr.update(type);
    }

    @Override
    public void delete(UUID id) {
        tr.deleteById(id);
    }

    @Override
    public Optional<TypeDocument> findById(UUID id) {
        return tr.findById(id);
    }

    @Override
    public List<TypeDocument> allTypes() {
        return tr.findAll();
    }
}
