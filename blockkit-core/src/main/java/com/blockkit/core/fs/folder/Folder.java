package com.blockkit.core.fs.folder;

import java.nio.file.Path;
import java.util.Objects;

public class Folder {
    private final Path path;
    private final String name;

    private Folder(Builder builder) {
        this.name = builder.name;
        this.path = builder.parentPath.resolve(builder.name);
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Path parentPath;
        private String name;

        public Builder parentPath(Path parentPath) {
            this.parentPath = parentPath;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Folder build() {
            Objects.requireNonNull(parentPath, "parentPath is required");
            Objects.requireNonNull(name, "name is required");
            return new Folder(this);
        }
    }
}
