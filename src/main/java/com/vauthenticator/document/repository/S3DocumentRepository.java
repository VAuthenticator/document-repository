package com.vauthenticator.document.repository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static com.vauthenticator.document.repository.SafeOperationExecutor.readAllBytesFrom;

public class S3DocumentRepository implements DocumentRepository {
    private final S3Client s3Client;
    private final String buketName;

    public S3DocumentRepository(S3Client s3Client, String buketName) {
        this.s3Client = s3Client;
        this.buketName = buketName;
    }


    public Document loadDocument(String type, String path) {
        var request = GetObjectRequest.builder().bucket(buketName).key(documentKeyFor(type, path)).build();
        var response = s3Client.getObject(request);
        return new Document(
                response.response().contentType(),
                path,
                readAllBytesFrom(response)
        );
    }



    public void saveDocument(String type, Document document) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(buketName)
                        .key(documentKeyFor(type, document.path()))
                        .contentType(document.contentType())
                        .build(),
                RequestBody.fromBytes(document.content())
        );
    }

}