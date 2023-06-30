package com.vauthenticator.document.repository;

import com.vauthenticator.document.support.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemDocumentRepositoryTest {

    private FileSystemDocumentRepository documentRepository = new FileSystemDocumentRepository("src/test/resources");

    @Test
    void load_document_from_FileSystem() {
        var document = documentRepository.loadDocument("mail", "/index.html");

        var expected = FileUtils.loadFileFor("/mail/index.html");
        assertEquals(expected, new String(document.content()));
    }

    @Test
    void store_document_on_FileSystem() {
        var documentFile = FileUtils.loadFileFor("/mail/index.html");
        var document = new Document("text/html", "/index.html", documentFile.getBytes());

        documentRepository.saveDocument("mail", document);

        var actual = documentRepository.loadDocument("mail", "/index.html");
        assertEquals(new String(actual.content()), new String(document.content()));
    }

}