package com.vauthenticator.document.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
class DocumentRepositoryConfig {

    @Bean("documentRepository")
    @ConditionalOnProperty(value = "document.engine", havingValue = "s3")
    DocumentRepository s3DocumentRepository(@Value("${document.bucket-name}") String documentBucketName,
                                            S3Client s3Client) {
        return new S3DocumentRepository(s3Client, documentBucketName);
    }


    @Bean("documentRepository")
    @ConditionalOnProperty(value = "document.engine", havingValue = "file-system")
    DocumentRepository fileSystemDocumentRepository(@Value("${document.fs-base-path}") String basePath) {
        return new FileSystemDocumentRepository(basePath);
    }


    @Bean
    CaffeineCache mailDocumentLocalCache(
            DocumentRepository documentRepository,
            @Value("${document.document-type.mail.cache.name:mail-document-local-cache}") String cacheName,
            @Value("${document.document-type.mail.cache.ttl:1m}") Duration ttl
    ) {

        return new CaffeineCache(cacheName, Caffeine.newBuilder()
                .refreshAfterWrite(ttl)
                .build(assetName -> documentRepository.loadDocument(DocumentType.MAIL.getContent(), "templates/%s".formatted(assetName))));

    }
    @Bean
    CaffeineCache staticAssetDocumentLocalCache(
            DocumentRepository documentRepository,
            @Value("${document.document-type.static-asset.cache.name:static-asset-document-local-cache}") String cacheName,
            @Value("${document.document-type.static-asset.cache.ttl:1m}") Duration ttl
    ) {

        return new CaffeineCache(cacheName, Caffeine.newBuilder()
                .refreshAfterWrite(ttl)
                .build(assetName -> documentRepository.loadDocument(DocumentType.STATIC_ASSET.getContent(), "content/asset/%s".formatted(assetName))));

    }

}