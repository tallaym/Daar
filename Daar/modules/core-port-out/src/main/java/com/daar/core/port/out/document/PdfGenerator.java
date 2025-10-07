package com.daar.core.port.out.document;

import java.util.UUID;

public interface PdfGenerator {
    String generatePDF(UUID documentId);
}
