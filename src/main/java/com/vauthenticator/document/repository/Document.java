package com.vauthenticator.document.repository;

import java.util.Arrays;
import java.util.Objects;

public record Document(String contentType, String path, byte[] content) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(contentType, document.contentType) && Objects.equals(path, document.path) && Arrays.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(contentType, path);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
