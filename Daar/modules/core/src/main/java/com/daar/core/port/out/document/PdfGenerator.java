package com.daar.core.port.out.document;

import com.daar.core.model.document.Document;

public interface PdfGenerator {
    void generate(Document doc);
}
