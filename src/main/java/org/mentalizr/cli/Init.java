package org.mentalizr.cli;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.util.fileLocator.FileLocator;
import org.mentalizr.cli.util.fileLocator.FileLocatorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Init {

    public static void init() {
        try {
            Path m7rConfigDirPath = createM7rConfigDir();
            Path m7rCliConfigDirPath = createM7rCliConfigDir(m7rConfigDirPath);
            createCliConfigFile(m7rCliConfigDirPath);
        } catch (IOException | FileLocatorException e) {
            throw new CliException(e);
        }
    }

    private static Path createM7rConfigDir() throws IOException {
        Path m7rConfigDirPath = Paths.get(System.getProperty("user.home"), Const.M7R_CONFIG_DIR);
        if (!Files.exists(m7rConfigDirPath)) {
            Files.createDirectory(m7rConfigDirPath);
        }
        return m7rConfigDirPath;
    }

    private static Path createM7rCliConfigDir(Path m7rConfigDirPath) throws IOException {
        Path m7rCliConfigDirPath = Paths.get(m7rConfigDirPath.toString(), Const.M7R_CLI_CONFIG_SUB_DIR);
        if (!Files.exists(m7rCliConfigDirPath)) {
            Files.createDirectory(m7rCliConfigDirPath);
        }
        return m7rCliConfigDirPath;
    }

    private static void createCliConfigFile(Path m7rCliConfigDirPath) throws FileLocatorException, IOException {
        Path m7rCliConfigFile = Paths.get(m7rCliConfigDirPath.toString(), Const.M7R_CLI_CONFIG_FILE);
        if (!Files.exists(m7rCliConfigFile)) {
            FileLocator fileLocator = FileLocator.fromClasspath("cli.config");
            Files.copy(fileLocator.asInputStream(), m7rCliConfigFile);
        }
    }

}
