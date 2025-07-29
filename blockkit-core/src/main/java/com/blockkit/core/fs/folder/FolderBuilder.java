package com.blockkit.core.fs.folder;

import java.nio.file.Path;
import java.util.Objects;

public class FolderBuilder {
    private Path parentPath;
    private String name;

    public FolderBuilder parentPath(Path parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public FolderBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Folder build() {
        Objects.requireNonNull(parentPath, "parentPath is required");
        Objects.requireNonNull(name, "name is required");
        return Folder.builder()
                     .parentPath(parentPath)
                     .name(name)
                     .build();
    }
}
