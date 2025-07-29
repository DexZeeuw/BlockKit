package com.blockkit.core.fs.file;

import java.io.IOException;

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
}
