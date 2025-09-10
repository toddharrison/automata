package com.eharrison.automata;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class Files {
    public static Optional<File[]> getFilesInDirectory(final File dir, final String extension) {
        return Optional.ofNullable(dir.listFiles((dir1, name) ->
                name.toLowerCase().endsWith(extension.toLowerCase())));
    }

    public static URL[] filesToUrls(final File[] files) {
        return Arrays.stream(files)
                .map(Files::getFileUrl)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(URL[]::new);
    }

    public static Optional<URL> getFileUrl(final File file) {
        try {
            return Optional.of(file.toURI().toURL());
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
