package com.blockkit.core.fs.folder;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderServiceImpl implements FolderService {

    @Override
    public Folder createFolder(Folder folder) throws IOException {
        Path target = folder.getPath();
        if (Files.exists(target)) {
            throw new IOException("Folder already exists: " + target);
        }
        Files.createDirectories(target);
        return folder;
    }

    @Override
    public Folder renameFolder(String folderId, String newName) throws IOException {
        Path source = Paths.get(folderId);
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new FolderNotFoundException("Folder not found: " + folderId);
        }
        Path target = source.getParent().resolve(newName);
        if (Files.exists(target)) {
            throw new IOException("Folder not found: " + target);
        }
        Files.move(source, target);
        return Folder.builder()
                     .parentPath(target.getParent())
                     .name(target.getFileName().toString())
                     .build();
    }

    @Override
    public void deleteFolder(String folderId) throws IOException {
        Path path = Paths.get(folderId);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new FolderNotFoundException("Folder not found: " + folderId);
        }
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public Folder copyFolder(String sourceFolderId,
                             String destParentFolderId,
                             boolean includeContents) throws IOException {
        Path source = Paths.get(sourceFolderId);
        Path destParent = Paths.get(destParentFolderId);
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new FolderNotFoundException("Source folder not found: " + source);
        }
        if (!Files.exists(destParent) || !Files.isDirectory(destParent)) {
            throw new FolderNotFoundException("Target parent not found: " + destParent);
        }
        Path target = destParent.resolve(source.getFileName());
        if (Files.exists(target)) {
            throw new IOException("Folder already exists: " + target);
        }
        if (includeContents) {
            Files.walkFileTree(source, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Path rel = source.relativize(dir);
                    Files.createDirectories(target.resolve(rel));
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    Path rel = source.relativize(file);
                    Files.copy(file, target.resolve(rel));
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            Files.createDirectories(target);
        }
        return Folder.builder()
                     .parentPath(target.getParent())
                     .name(target.getFileName().toString())
                     .build();
    }

    @Override
    public Folder moveFolder(String sourceFolderId,
                             String destParentFolderId) throws IOException {
        Path source = Paths.get(sourceFolderId);
        Path destParent = Paths.get(destParentFolderId);
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new FolderNotFoundException("Source folder not found: " + source);
        }
        if (!Files.exists(destParent) || !Files.isDirectory(destParent)) {
            throw new FolderNotFoundException("Target parent not found: " + destParent);
        }
        Path target = destParent.resolve(source.getFileName());
        if (Files.exists(target)) {
            throw new IOException("Folder already exists at target location: " + target);
        }
        try {
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (FileSystemException e) {
            copyFolder(sourceFolderId, destParentFolderId, true);
            deleteFolder(sourceFolderId);
        }
        return Folder.builder()
                     .parentPath(target.getParent())
                     .name(target.getFileName().toString())
                     .build();
    }
}
