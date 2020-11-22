package org.mentalizr.cli.config;

import java.io.File;

public class CliConfigurationFiles {

    private static final String CONFIG_DIR_M7R = ".m7r";
    private static final String CONFIG_DIR_CLI = "cli";
    private static final String BACKUP_DIR = "backup";
    private static final String CONFIG_FILE = "cli.config";

    public static File getDefaultConfigFileCLI() {
        String configFileAsString = getConfigDirCLI() + "/" + CONFIG_FILE;
        return new File(configFileAsString);
    }

    public static File getConfigDirCLI() {
        String configDirCliAsString = System.getProperty("user.home") + "/" + CONFIG_DIR_M7R + "/" + CONFIG_DIR_CLI;
        return new File(configDirCliAsString);
    }

    public static File getBackupRootDir() {
        String backupDirAsString = System.getProperty("user.home") + "/" + CONFIG_DIR_M7R + "/" + BACKUP_DIR;
        return new File(backupDirAsString);
    }

}
