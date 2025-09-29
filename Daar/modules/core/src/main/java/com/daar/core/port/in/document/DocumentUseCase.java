package com.daar.core.port.in.document;

import com.daar.core.model.document.Document;
import com.daar.core.model.document.Type;

import java.util.*;

public interface DocumentUseCase {

    UUID createDocument(Document doc);
    Document updateDocument(UUID id, Document doc);
    Document getDocumentById(UUID id);
    List<Document> listDocuments();
    List<Document> listSub(UUID id);
    UUID getParentId(UUID id);
    void generatePdf(UUID id);
    void sendWarning(UUID id);
    List<Document> addedAfter(Date start);
    List<Document> addedBetween(Date start, Date end);



}
