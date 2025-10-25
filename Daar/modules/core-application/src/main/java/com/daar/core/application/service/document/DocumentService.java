package com.daar.core.application.service.document;

import com.daar.core.domain.model.document.Document;
import com.daar.core.port.in.usecase.document.DocumentUseCase;
import com.daar.core.port.out.document.DocumentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DocumentService implements DocumentUseCase {

    private final DocumentRepository dr;

    public DocumentService(DocumentRepository dr) {
        this.dr = dr;
    }

    @Override
    public UUID create(Document doc) {
        return dr.insert(doc).getId();
    }

    @Override
    public Document update(Document doc) {
        return dr.update(doc);
    }

    @Override
    public Optional<Document> getDocumentById(UUID id) {
        return dr.findById(id);
    }

    @Override
    public List<Document> listDocuments() {
        return dr.findAll();
    }

    @Override
    public List<Document> listSub(UUID id) {
        return dr.findSubDocuments(id);
    }




    @Override
    public List<Document> addedAfter(Date start) {
        return dr.findAddedAfter(start);
    }

    @Override
    public List<Document> addedBetween(Date start, Date end) {
        return dr.findAddedBetween(start, end);
    }

    @Override
    public List<Document> affiliatedToUser(UUID user) {
        return dr.findByUserId(user);
    }

    @Override
    public List<Document> addedByUser(UUID author) {
        return dr.findByAuthorId(author);
    }


}
