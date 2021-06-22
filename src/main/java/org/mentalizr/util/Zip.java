package org.mentalizr.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Zip {

    /**
     * Creates a zip file for specified source (directory or file). All timestamps are preserved. Empty directories
     * are preserved. Symbolic links are ignored. If includeRootDir is set to true, source directory is included
     * into zip, otherwise all contained child files and directories will be created as root elements of the zip
     * file.
     *
     * @param source
     * @param destination
     * @param includeRootDir
     * @throws IOException
     */
    public static void zip(final Path source, final Path destination, final boolean includeRootDir) throws IOException {

        if (!Files.exists(source))
            throw new IllegalArgumentException("Source path for zip creation not existing: " +
                    "[" + source.toAbsolutePath() + "].");

        if (Files.exists(destination))
            throw new IllegalArgumentException("Destination path for zip creation already existing: " +
                    "[" + destination.toAbsolutePath() + "].");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(destination))) {

            Files.walkFileTree(source, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {

                    if (attributes.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    Path reference = getReference(source, includeRootDir);
                    ZipEntry zipEntry = new ZipEntry(reference.relativize(file).toString());
                    preserveTimestamps(zipEntry, attributes);
                    zipOutputStream.putNextEntry(zipEntry);
                    Files.copy(file, zipOutputStream);
                    zipOutputStream.closeEntry();

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) throws IOException {

                    if (attributes.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    Path reference = getReference(source, includeRootDir);
                    ZipEntry zipEntry = new ZipEntry(reference.relativize(dir) + "/");
                    preserveTimestamps(zipEntry, attributes);
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.closeEntry();

                    return FileVisitResult.CONTINUE;
                }

            });
        }
    }

    private static Path getReference(Path source, boolean includeRootDir) {
        if (Files.isDirectory(source)) {
            return includeRootDir ? source.getParent() : source;
        } else if (Files.isRegularFile(source)) {
            return source.getParent();
        }
        throw new RuntimeException("Illegal file type for source of zip creation: [" + source.toAbsolutePath() + "].");
    }

    private static void preserveTimestamps(ZipEntry zipEntry, BasicFileAttributes attributes) {
        zipEntry.setCreationTime(attributes.creationTime());
        zipEntry.setLastModifiedTime(attributes.lastModifiedTime());
        zipEntry.setLastAccessTime(attributes.lastAccessTime());
    }

    public static List<? extends ZipEntry> getZipEntryList(Path zipPath) throws IOException {
        if (!Files.exists(zipPath) || !Files.isRegularFile(zipPath))
            throw new IllegalArgumentException("Specified zip file not existing: [" + zipPath.toAbsolutePath() + "].");

        ZipFile zipFile = new ZipFile(zipPath.toFile());
        return Collections.list(zipFile.entries());
    }

}
