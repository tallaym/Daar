package com.daar.core.port.out.document;

import com.daar.core.model.document.Document;

import java.util.*;

public interface DocumentRepository {

    Document insert(Document doc);
    Document update(Document doc);
    Optional<Document> findById(UUID id);
    void deleteById(UUID id);
    List<Document> findAll();
    List<Document> findSubDocuments(UUID parentId);
    List<Document> findAddedAfter(Date start);
    List<Document> findAddedBetween(Date start, Date end);




}
