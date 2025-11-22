package com.daar.core.domain.repository.document;

import java.util.UUID;

public interface PdfGenerator {
    String generatePDF(UUID documentId);
}
