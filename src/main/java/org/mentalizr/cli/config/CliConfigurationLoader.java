package org.mentalizr.cli.config;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;
import org.mentalizr.cli.exceptions.CliException;

import java.io.File;
import java.io.IOException;

public class CliConfigurationLoader {

    private static final String PROPERTY__SERVER = "server";
    private static final String PROPERTY__TRUST_ALL = "trust_all";
    private static final String PROPERTY__PROXY_SERVER = "proxy_server";
    private static final String PROPERTY__PROXY_PORT = "proxy_port";
    private static final String PROPERTY__PROXY_USER = "proxy_user";
    private static final String PROPERTY__PROXY_PASSWORD = "proxy_password";

    public static CliConfiguration load() {
        File configFile = CliConfigurationFiles.getDefaultConfigFileCLI();
        return load(configFile);
    }

    public static CliConfiguration load(File configFile) {

        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        bindConfigFile(configurationFactory, configFile);

        Configuration configuration = configurationFactory.getConfiguration();

        String server = configuration.getString(PROPERTY__SERVER, "");
        boolean trustAll = configuration.getBoolean(PROPERTY__TRUST_ALL, false);
        String proxyServer = configuration.getString(PROPERTY__PROXY_SERVER, "");
        int proxyPort = configuration.getInt(PROPERTY__PROXY_PORT, 0);
        String proxyUser = configuration.getString(PROPERTY__PROXY_USER, "");
        String proxyPassword = configuration.getString(PROPERTY__PROXY_PASSWORD, "");

        CliConfiguration cliConfiguration = new CliConfiguration(server);
        cliConfiguration.setTrustAll(trustAll);
        cliConfiguration.setProxyServer(proxyServer);
        cliConfiguration.setProxyPort(proxyPort);
        cliConfiguration.setProxyServerUser(proxyUser);
        cliConfiguration.setProxyServerPassword(proxyPassword);

        return cliConfiguration;
    }

    private static void bindConfigFile(ConfigurationFactory configurationFactory, File configFile) {
        try {
            configurationFactory.addConfigurationFileFromFilesystem(configFile);
        } catch (ConfigurationFileNotFoundException | IOException e) {
            throw new CliException("CliConfiguration file not found: " + configFile.getAbsolutePath());
        }
    }

}
