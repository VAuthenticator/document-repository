# document-repository

````yaml
document:
  engine: s3 | file-system
  fs-base-path: your-file-system-document-base-path
  document-type:
    mail:
      cache:
        name: ...
        ttl: 1m
    static-asset:
      cache:
        name: ...
        ttl: 1m
````