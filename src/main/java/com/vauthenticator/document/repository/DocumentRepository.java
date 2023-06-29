package com.vauthenticator.document.repository;

public interface DocumentRepository {
    Document loadDocument(String type, String path);

    void saveDocument(String type, Document document);

    default String documentKeyFor(String type, String path) {
        return "%s/%s".formatted(type, path);
    }

}
