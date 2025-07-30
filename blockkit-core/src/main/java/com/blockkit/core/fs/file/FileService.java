package com.blockkit.core.fs.file;

import com.blockkit.core.fs.folder.Folder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {

    FileResource createFile(FileResource file) throws IOException;

    FileResource renameFile(String fileId, String newName) throws IOException;

    void deleteFile(String fileId) throws IOException;

    // Overloads voor OO-stijl
    default FileResource renameFile(FileResource file, String newName) throws IOException {
        return renameFile(file.getPath().toString(), newName);
    }
    default void deleteFile(FileResource file) throws IOException {
        deleteFile(file.getPath().toString());
    }
    /**
     * Haalt alle bestanden in de gegeven folder op.
     */
    List<FileResource> listFiles(Folder folder) throws IOException;

    /**
     * Verplaatst een bestand naar een nieuwe folder.
     */
    FileResource moveFile(FileResource file, Folder destinationFolder) throws IOException;

    /**
     * Verplaatst een bestand naar een specifiek pad.
     */
    FileResource moveFile(FileResource file, Path destinationPath) throws IOException;
}
