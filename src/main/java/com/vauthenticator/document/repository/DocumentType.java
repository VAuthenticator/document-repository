package com.vauthenticator.document.repository;

public enum DocumentType {
    MAIL("mail"), STATIC_ASSET("static-asset");

    private String content;

    DocumentType(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
