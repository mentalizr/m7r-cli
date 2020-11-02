package org.mentalizr.cli.util.stringFilePersister;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Simple functionality for reading and writing a string to/from a file.
 * Implementation is thread save as long all file operations are processed
 * using the identical instance of that class.
 */
public class StringFilePersister {

    private Path path;
    private Charset charset;

    public StringFilePersister(Path path) {
        this.path = path;
        this.charset = StandardCharsets.UTF_8;
    }

    public StringFilePersister(Path path, Charset charset) {
        this.path = path;
        this.charset = charset;
    }

    public synchronized void write(String string) throws IOException {
        Files.writeString(this.path, string, this.charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public synchronized String read() throws IOException {
        return Files.readString(this.path, this.charset);
    }

    public synchronized void delete() throws IOException {
        Files.delete(this.path);
    }

    public synchronized boolean exists() {
        return Files.exists(this.path);
    }

}
