package com.blockkit.core.fs.file;

import com.blockkit.core.fs.folder.Folder;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<FileResource> listFiles(Folder folder) throws IOException {
        Path base = folder.getPath();
        List<FileResource> result = new ArrayList<>();

        if (Files.notExists(base)) {
            return result;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(base)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    FileResource file = FileResource.builder()
                            .parentPath(base)
                            .name(entry.getFileName().toString())
                            .build();
                    result.add(file);
                }
            }
        }

        return result;
    }
}
