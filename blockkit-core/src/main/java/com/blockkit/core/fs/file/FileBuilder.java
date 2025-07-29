package com.blockkit.core.fs.file;

import java.nio.file.Path;
import java.util.Objects;

public class FileBuilder {
    private Path parentPath;
    private String name;

    public FileBuilder parentPath(Path parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public FileBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FileResource build() {
        Objects.requireNonNull(parentPath, "parentPath is required");
        Objects.requireNonNull(name, "name is required");
        return FileResource.builder()
                           .parentPath(parentPath)
                           .name(name)
                           .build();
    }
}
