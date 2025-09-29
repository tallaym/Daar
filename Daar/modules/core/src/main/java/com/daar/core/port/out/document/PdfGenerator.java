package com.daar.core.port.out.document;

import com.daar.core.model.document.Document;

import java.util.UUID;

public interface PdfGenerator {
    String generatePDF(UUID documentId);
}
