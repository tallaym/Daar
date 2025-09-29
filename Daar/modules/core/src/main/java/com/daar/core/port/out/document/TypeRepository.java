package com.daar.core.port.out.document;

import com.daar.core.model.document.Type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeRepository {

    Type insert(Type type);
    Type update(Type type);
    Optional<Type> findById(UUID id);
    void deleteById(UUID id);
    List<Type> findAll();
}
