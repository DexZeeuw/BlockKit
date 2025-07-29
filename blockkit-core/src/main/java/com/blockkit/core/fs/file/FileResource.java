package com.blockkit.core.fs.file;

import java.nio.file.Path;
import java.util.Objects;

public class FileResource {
    private final Path path;
    private final String name;

    private FileResource(Builder builder) {
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

        public FileResource build() {
            Objects.requireNonNull(parentPath, "parentPath is required");
            Objects.requireNonNull(name, "name is required");
            return new FileResource(this);
        }
    }
}
