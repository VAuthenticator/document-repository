package com.vauthenticator.document.repository;

import com.vauthenticator.document.support.FileUtils;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;

import static com.vauthenticator.document.support.DocumentUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class S3DocumentRepositoryTest {

    @Test
    void load_document_from_S3() {
        S3Client s3Client = s3Client();
        initDocumentTests(s3Client);

        var documentRepository = new S3DocumentRepository(s3Client, documentBucket);
        var document = documentRepository.loadDocument("mail", "templates/index.html");

        var expected = FileUtils.loadFileFor("/index.html");
        assertEquals(expected, new String(document.content()));
    }

    @Test
    void store_document_on_S3() {
        S3Client s3Client = s3Client();
        initDocumentTests(s3Client);

        var documentFile = FileUtils.loadFileFor("/index.html");
        var document = new Document("text/html", "templates/index.html", documentFile.getBytes());

        var documentRepository = new S3DocumentRepository(s3Client, documentBucket);

        documentRepository.saveDocument("mail", document);

        var actual = documentRepository.loadDocument("mail", "templates/index.html");
        assertEquals(actual, document);
    }
}