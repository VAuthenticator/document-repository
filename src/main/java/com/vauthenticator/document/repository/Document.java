package com.vauthenticator.document.repository;

public record Document(String contentType, String path, byte[] content) {
}
