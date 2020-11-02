package org.mentalizr.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Const {

    public static final String VERSION = "0.0.1-SNAPSHOT";
    public static final String CONTEXT_PATH = "/netstep/service/v1/";
    public static final String M7R_CONFIG_DIR = ".m7r";
    public static final String M7R_CLI_CONFIG_SUB_DIR = "cli";
    public static final String M7R_CLI_CONFIG_FILE = "cli.config";

    public static Path getCliConfigPath() {
        return Paths.get(
                System.getProperty("user.home"),
                M7R_CONFIG_DIR,
                M7R_CLI_CONFIG_SUB_DIR,
                M7R_CLI_CONFIG_FILE
        );
    }

}
