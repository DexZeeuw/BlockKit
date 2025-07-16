package com.blockkit.fs.extras;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * WatchService-wrapper om bestandswijzigingen in een directory te detecteren.
 */
public final class DirWatcher {
    private final WatchService watcher;
    private final Path dir;
    private final Consumer<WatchEvent<?>> callback;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private DirWatcher(Path dir, Consumer<WatchEvent<?>> callback) throws IOException {
        this.dir = dir;
        this.callback = callback;
        this.watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public static DirWatcher of(Path dir, Consumer<WatchEvent<?>> callback) throws IOException {
        return new DirWatcher(dir, callback);
    }

    /** Start luisteren in een aparte thread. */
    public void start() {
        executor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watcher.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        callback.accept(event);
                    }
                    key.reset();
                }
            } catch (InterruptedException ignored) {}
        });
    }

    /** Stop luisteren en sluit resources. */
    public void stop() throws IOException {
        executor.shutdownNow();
        watcher.close();
    }
}
