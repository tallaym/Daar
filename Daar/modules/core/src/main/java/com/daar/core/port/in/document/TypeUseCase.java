package com.daar.core.port.in.document;

import com.daar.core.model.document.Type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeUseCase {

    UUID createTypeDoc(Type t);
    Type updateTypeDoc(UUID id, Type type);
    Type deleteTypeDoc(UUID id);
    Type getTypeById(UUID id);
    List<Type> listTypeDoc();
}
