package org.mentalizr.cli.config;

import java.io.File;

public class CliConfigurationFiles {

    private static final String CONFIG_DIR_M7R = ".m7r";
    private static final String CONFIG_DIR_CLI = "cli";
    private static final String BACKUP_DIR = "backup";
    private static final String CREATE_DIR = "create";
    private static final String CONFIG_FILE = "cli.config";

    public static File getDefaultConfigFileCLI() {
        String configFileAsString = getConfigDirCLI() + "/" + CONFIG_FILE;
        return new File(configFileAsString);
    }

    public static File getConfigDirCLI() {
        String configDirCliAsString = System.getProperty("user.home") + "/" + CONFIG_DIR_M7R + "/" + CONFIG_DIR_CLI;
        return new File(configDirCliAsString);
    }

    public static File getDefaultBackupRootDir() {
        String backupDirAsString = System.getProperty("user.home") + "/" + CONFIG_DIR_M7R + "/" + BACKUP_DIR;
        return new File(backupDirAsString);
    }

    public static File getCreateDir() {
        String backupDirAsString = System.getProperty("user.home") + "/" + CONFIG_DIR_M7R + "/" + CREATE_DIR;
        return new File(backupDirAsString);
    }


}
