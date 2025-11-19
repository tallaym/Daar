package com.daar.core.domain.port_out.document;

import java.util.UUID;

public interface PdfGenerator {
    String generatePDF(UUID documentId);
}
