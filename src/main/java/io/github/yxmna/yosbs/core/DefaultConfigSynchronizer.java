package io.github.yxmna.yosbs.core;

import io.github.yxmna.yosbs.util.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import java.util.Arrays;
import java.util.Collections;

/**
 * Synchronises default configuration files shipped with a modpack
 * into the live Minecraft directory only if> those files are
 * currently missing.
 */
public final class DefaultConfigSynchronizer {

    private final Logger logger;
    private final Path   mcDir;      // e.g. ~/.minecraft
    private final Path   configDir;  // e.g. ~/.minecraft/config
    private final List<Path> roots;  // ~/.minecraft/config/yosbs , ~/.minecraft/config/yosbr

    private int visited = 0;
    private int copied  = 0;

    public DefaultConfigSynchronizer(Logger logger, Path mcDir, Path configDir) {
        this.logger     = logger;
        this.mcDir      = mcDir;
        this.configDir  = configDir;
        this.roots = Collections.unmodifiableList(Arrays.asList(
                configDir.resolve("yosbs"),
                configDir.resolve("yosbr")
        ));
    }

    /**
     * Scans roots and copies every default
     * file that is still absent from the live game directory.
     */
    public void apply() {
        logger.info("Searching for default configuration files ...");

        for (Path root : roots) {
            if (!Files.exists(root)) {
                logger.debug("Root folder not found, skipping: {}", root);
                continue;
            }
            try {
                // depth = Integer.MAX_VALUE, no options ⇒ no symlinks followed
                Files.walkFileTree(root, new CopyVisitor(root));
            } catch (IOException e) {
                logger.error("Error while processing {}", root, e);
            }
        }

        /* ---------- summary ---------- */
        if (visited == 0) {
            logger.info("No default configuration files were found.");
        } else if (copied == 0) {
            logger.info("All {} configuration file(s) were already present.", visited);
        } else {
            logger.info("Copied {} new configuration file(s); {} already existed.", copied, visited - copied);
        }
        logger.info("Finished.");
    }

    /* --------------------------------------------------------------------- */
    /* Internal helpers                                                      */
    /* --------------------------------------------------------------------- */

    private final class CopyVisitor extends SimpleFileVisitor<Path> {

        private final Path currentRoot;

        CopyVisitor(Path currentRoot) {
            this.currentRoot = currentRoot;
        }

        @Override
        public FileVisitResult visitFile(Path src, BasicFileAttributes attrs) throws IOException {
            if (!attrs.isRegularFile()) return FileVisitResult.CONTINUE;

            visited++;

            Path dst = mapToDestination(currentRoot, src);
            if (dst == null) {
                logger.debug("File outside mapping scope, ignored: {}", src);
                return FileVisitResult.CONTINUE;
            }

            if (Files.exists(dst)) {
                logger.info("Configuration file already present, skipping: {}",
                        mcDir.relativize(dst));
                return FileVisitResult.CONTINUE;
            }

            /* ---------- atomic copy ---------- */
            try {
                Files.createDirectories(dst.getParent());

                Path tmp = dst.resolveSibling(dst.getFileName() + ".tmp");
                Files.copy(src, tmp,
                        StandardCopyOption.COPY_ATTRIBUTES,
                        StandardCopyOption.REPLACE_EXISTING);

                Files.move(tmp, dst,
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);

                copied++;
                logger.info("Copied {} to {}",
                        currentRoot.getParent().relativize(src),
                        mcDir.relativize(dst));

            } catch (IOException e) {
                logger.error("Failed to copy {} to {}", src, dst, e);
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            logger.error("Cannot access {}", file, exc);
            return FileVisitResult.CONTINUE; // keep scanning
        }
    }

    /**
     * Maps a source file located under one of the roots to its destination
     * in the live Minecraft directory.
     */
    private Path mapToDestination(Path root, Path sourceFile) {
        Path rel = root.relativize(sourceFile); // e.g. options.txt or config/foo.json

        // ~/.minecraft/config/**
        if (rel.getNameCount() > 0 && "config".equals(rel.getName(0).toString()))
            return configDir.resolve(rel.subpath(1, rel.getNameCount()));

        // ~/.minecraft/**
        return mcDir.resolve(rel);
    }
}
