package com.vauthenticator.document.repository;

public enum DocumentType {
    MAIL("mail"), STATIC("static-auth-server");

    private final String content;

    DocumentType(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
