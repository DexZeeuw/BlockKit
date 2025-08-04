package com.blockkit.core.fs.folder;

import java.nio.file.Path;
import java.util.Objects;

public class FolderBuilder {
    private Path parentPath;
    private String name;
    private Path fullPath;     // optioneel: het volledige pad

    /** Bouw op basis van parentPath + name */
    public FolderBuilder parentPath(Path parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public FolderBuilder name(String name) {
        this.name = name;
        return this;
    }

    /** Bouw op basis van een volledig Path */
    public FolderBuilder path(Path fullPath) {
        this.fullPath = fullPath;
        return this;
    }

    public Folder build() {
        if (fullPath != null) {
            // gebruik het absolute pad
            Path p = fullPath;
            Path parent = p.getParent();
            String nm = p.getFileName().toString();
            Objects.requireNonNull(parent, "parentPath is required");
            Objects.requireNonNull(nm,     "name is required");
            return Folder.builder()
                         .parentPath(parent)
                         .name(nm)
                         .build();
        }

        // fallback op parentPath + name
        Objects.requireNonNull(parentPath, "parentPath is required");
        Objects.requireNonNull(name,       "name is required");
        return Folder.builder()
                     .parentPath(parentPath)
                     .name(name)
                     .build();
    }
}
