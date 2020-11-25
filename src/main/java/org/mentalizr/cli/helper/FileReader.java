package org.mentalizr.cli.helper;

import org.mentalizr.cli.exceptions.CliException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public static String fromFile(Path file) {

        if (!Files.exists(file)) {
            throw new CliException("File [" + file.toAbsolutePath() + "] not found.");
        }

        String json;
        try {
            json = Files.readString(file);
        } catch (IOException e) {
            throw new CliException("Could not read file [" + file.toAbsolutePath() + "]: " + e.getMessage(), e);
        }

        return json;
    }

}
