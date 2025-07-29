package com.blockkit.core.fs.file;

import java.io.IOException;
import java.nio.file.*;

public class FileServiceImpl implements FileService {

    @Override
    public FileResource createFile(FileResource file) throws IOException {
        Path target = file.getPath();
        if (Files.exists(target)) {
            throw new IOException("File already exists: " + target);
        }
        Files.createDirectories(target.getParent());
        Files.createFile(target);
        return file;
    }

    @Override
    public FileResource renameFile(String fileId, String newName) throws IOException {
        Path source = Paths.get(fileId);
        if (!Files.exists(source) || Files.isDirectory(source)) {
            throw new FileResourceNotFoundException("File not found: " + fileId);
        }
        Path target = source.getParent().resolve(newName);
        if (Files.exists(target)) {
            throw new IOException("File already exists: " + target);
        }
        Files.move(source, target);
        return FileResource.builder()
                           .parentPath(target.getParent())
                           .name(target.getFileName().toString())
                           .build();
    }

    @Override
    public void deleteFile(String fileId) throws IOException {
        Path path = Paths.get(fileId);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new FileResourceNotFoundException("File not found: " + fileId);
        }
        Files.delete(path);
    }
}
