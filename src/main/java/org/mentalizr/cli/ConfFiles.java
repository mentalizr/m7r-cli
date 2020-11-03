package org.mentalizr.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfFiles {

    public static final String CONFIG_HOME = System.getProperty("user.home") + "/.m7r";

    public static final String CLI_DIR = CONFIG_HOME + "/cli";
    public static final String CLI_CONFIG_FILE = CLI_DIR + "/cli.config";

    public static final String SESSION_DIR = CLI_DIR + "/session";
    public static final String COOKIE_DIR = SESSION_DIR + "/cookie";

    public static final String COOKIE_NAME_FILE = COOKIE_DIR + "/name";
    public static final String COOKIE_VALUE_FILE = COOKIE_DIR + "/value";
    public static final String COOKIE_SERVER_FILE = COOKIE_DIR + "/server";
    public static final String COOKIE_PATH_FILE = COOKIE_DIR + "/path";

    public static Path getConfigHome() {
        return Paths.get(CONFIG_HOME);
    }

    public static Path getCliDir() {
        return Paths.get(CLI_DIR);
    }

    public static Path getSessionDir() {
        return Paths.get(SESSION_DIR);
    }

    public static boolean existsSessionDir() {
        return Files.exists(getSessionDir());
    }

    public static Path getCookieDir() {
        return Paths.get(COOKIE_DIR);
    }

    public static boolean existsCookieDir() {
        return Files.exists(getCookieDir());
    }

    public static Path getCookieNameFile() {
        return Paths.get(COOKIE_NAME_FILE);
    }

    public static Path getCookieValueFile() {
        return Paths.get(COOKIE_VALUE_FILE);
    }

    public static Path getCookieServerFile() {
        return Paths.get(COOKIE_SERVER_FILE);
    }

    public static Path getCookiePathFile() {
        return Paths.get(COOKIE_PATH_FILE);
    }

    public static Path getCliConfigFile() {
        return Paths.get(CLI_CONFIG_FILE);
    }

}
