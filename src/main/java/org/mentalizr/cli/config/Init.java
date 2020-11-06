package org.mentalizr.cli.config;

import org.mentalizr.cli.ConfFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.util.fileLocator.FileLocator;
import org.mentalizr.util.fileLocator.FileLocatorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Init {

    public static boolean isInitialized() {
        Path cliConfigFile = ConfFiles.getCliConfigFile();
        Path cookieDir = ConfFiles.getSessionDir();
        return Files.exists(cliConfigFile) && Files.exists(cookieDir);
    }

    public static void init() {
        try {
            createCliDir();
            copyCliConfigFile();
            createSessionDir();
        } catch (IOException | FileLocatorException e) {
            throw new CliException(e);
        }
    }

    private static void createCliDir() throws IOException {
        Path cliDir = ConfFiles.getCliDir();
        Files.createDirectories(cliDir);
    }

    private static void copyCliConfigFile() throws FileLocatorException, IOException {
        Path cliConfigFile = ConfFiles.getCliConfigFile();
        if (!Files.exists(cliConfigFile)) {
            FileLocator fileLocator = FileLocator.fromClasspath("cli.config");
            Files.copy(fileLocator.asInputStream(), cliConfigFile);
        }
    }

    private static void createSessionDir() throws IOException {
        Path cookieDir = ConfFiles.getSessionDir();
        Files.createDirectories(cookieDir);
    }

}
