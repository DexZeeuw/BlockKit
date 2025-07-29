package com.blockkit.core.fs.folder;

import java.io.IOException;
import java.util.List;

public interface FolderService {

    Folder createFolder(Folder folder) throws IOException;

    Folder renameFolder(String folderId, String newName) throws IOException;

    void deleteFolder(String folderId) throws IOException;

    Folder copyFolder(String sourceFolderId,
                      String destParentFolderId,
                      boolean includeContents) throws IOException;

    Folder moveFolder(String sourceFolderId,
                      String destParentFolderId) throws IOException;

    // Overloads voor OO-stijl
    default Folder renameFolder(Folder folder, String newName) throws IOException {
        return renameFolder(folder.getPath().toString(), newName);
    }
    default void deleteFolder(Folder folder) throws IOException {
        deleteFolder(folder.getPath().toString());
    }
    default Folder copyFolder(Folder source,
                              Folder destParent,
                              boolean includeContents) throws IOException {
        return copyFolder(source.getPath().toString(),
                          destParent.getPath().toString(),
                          includeContents);
    }
    default Folder moveFolder(Folder source,
                              Folder destParent) throws IOException {
        return moveFolder(source.getPath().toString(),
                          destParent.getPath().toString());
    }

    /**
     * Haalt alle directe subfolders van de gegeven folder op.
     */
    List<Folder> listFolders(Folder folder) throws IOException;

    /**
     * Controleert of er in de folder een bestand met de opgegeven naam voorkomt.
     *
     * @param folder   de folder waarin gezocht wordt
     * @param fileName de naam van het bestand (bijv. "test.txt")
     * @return true als het bestand bestaat, anders false
     */
    boolean hasFile(Folder folder, String fileName) throws IOException;
}
