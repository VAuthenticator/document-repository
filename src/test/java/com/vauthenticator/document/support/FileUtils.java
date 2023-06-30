package com.vauthenticator.document.support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private FileUtils() {
    }

    public static String loadFileFor(String path) {
        try {
            URL resource = DocumentUtils.class.getResource(path);
            Path resourcePath = Paths.get(resource.toURI());
            byte[] bytes = Files.readAllBytes(resourcePath);

            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
