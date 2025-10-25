package com.daar.core.port.in.usecase.document;


import com.daar.core.domain.model.document.Document;

import java.util.*;

public interface DocumentUseCase {

    UUID create(Document doc);
    Document update(Document doc);
    Optional<Document> getDocumentById(UUID id);
    List<Document> listDocuments();
    List<Document> listSub(UUID id);

    List<Document> addedAfter(Date start);
    List<Document> addedBetween(Date start, Date end);

    List<Document> affiliatedToUser(UUID userId);
    List<Document> addedByUser(UUID authorId);


}
