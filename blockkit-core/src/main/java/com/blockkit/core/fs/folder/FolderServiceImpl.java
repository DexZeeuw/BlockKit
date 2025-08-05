package com.blockkit.core.fs.folder;

import com.blockkit.BlockKit;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

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
    public Folder copyFolder(String sourceFolderId,
                             String destParentFolderId,
                             String newFolderName,
                             boolean includeContents) throws IOException {
        Path source     = Paths.get(sourceFolderId);
        Path destParent = Paths.get(destParentFolderId);

        // Validaties
        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new FolderNotFoundException("Source folder not found: " + source);
        }
        if (!Files.exists(destParent) || !Files.isDirectory(destParent)) {
            throw new FolderNotFoundException("Target parent not found: " + destParent);
        }

        // Bouw het targetpad met de nieuwe naam
        Path target = destParent.resolve(newFolderName);
        if (Files.exists(target)) {
            throw new IOException("Folder already exists: " + target);
        }

        // Kopieer inhoud of maak alleen de lege map
        if (includeContents) {
            Files.walkFileTree(source, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Path rel  = source.relativize(dir);
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

        // Retourneer de nieuwe Folder
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

    @Override
    public List<Folder> listFolders(Folder folder) throws IOException {
        Path base = folder.getPath();
        List<Folder> result = new ArrayList<>();

        if (Files.notExists(base)) {
            return result;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(base)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    Folder sub = Folder.builder()
                            .parentPath(base)
                            .name(entry.getFileName().toString())
                            .build();
                    result.add(sub);
                }
            }
        }

        return result;
    }

    @Override
    public boolean hasFile(Folder folder, String fileName) throws IOException {
        Path base = folder.getPath();
        Path target = base.resolve(fileName);

        // bestandscontrole: bestaat en is een regulier bestand
        return Files.exists(target) && Files.isRegularFile(target);
    }

    @Override
    public void restoreFolderContents(Folder fromFolder, Folder toFolder) throws IOException {
        Path source = fromFolder.getPath();
        Path target = toFolder.getPath();

        if (!Files.exists(source) || !Files.isDirectory(source)) {
            throw new FolderNotFoundException("Bronfolder bestaat niet: " + source);
        }
        if (!Files.exists(target) || !Files.isDirectory(target)) {
            Files.createDirectories(target); // Zorg dat doel bestaat
        }

        // Verplaats alle inhoud (geen rootfolder zelf, alleen inhoud)
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path entry : stream) {
                Path dest = target.resolve(entry.getFileName());
                Files.move(entry, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        // Verwijder lege source-folder
        Files.delete(source);
    }

}
