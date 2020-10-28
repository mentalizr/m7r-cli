package org.mentalizr.cli.config;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CliConfigurationTest {

    @Test
    void simpleConfig() {
        File configFile = new File("src/test/resources/config/simple.config");
        CliConfiguration cliConfiguration = CliConfigurationLoader.load(configFile);

        assertEquals("https://example.org", cliConfiguration.getServer());
        assertFalse(cliConfiguration.isTrustAll());
        assertFalse(cliConfiguration.hasProxyServer());
        assertFalse(cliConfiguration.hasProxyServerCredentials());
    }

    @Test
    void trustAll() {
        File configFile = new File("src/test/resources/config/trustAll.config");
        CliConfiguration cliConfiguration = CliConfigurationLoader.load(configFile);

        assertEquals("https://example.org", cliConfiguration.getServer());
        assertTrue(cliConfiguration.isTrustAll());
        assertFalse(cliConfiguration.hasProxyServer());
        assertFalse(cliConfiguration.hasProxyServerCredentials());
    }

    @Test
    void proxyConfig() {
        File configFile = new File("src/test/resources/config/proxy.config");
        CliConfiguration cliConfiguration = CliConfigurationLoader.load(configFile);

        assertEquals("https://example.org", cliConfiguration.getServer());
        assertFalse(cliConfiguration.isTrustAll());
        assertTrue(cliConfiguration.hasProxyServer());
        assertEquals("proxy.example.org", cliConfiguration.getProxyServer());
        assertEquals(8080, cliConfiguration.getProxyPort());
        assertFalse(cliConfiguration.hasProxyServerCredentials());
    }

    @Test
    void proxyConfigWithAuthentication() {
        File configFile = new File("src/test/resources/config/proxyAuth.config");
        CliConfiguration cliConfiguration = CliConfigurationLoader.load(configFile);

        assertEquals("https://example.org", cliConfiguration.getServer());
        assertFalse(cliConfiguration.isTrustAll());
        assertTrue(cliConfiguration.hasProxyServer());
        assertEquals("proxy.example.org", cliConfiguration.getProxyServer());
        assertEquals(8080, cliConfiguration.getProxyPort());
        assertTrue(cliConfiguration.hasProxyServerCredentials());
        assertEquals("dummy", cliConfiguration.getProxyServerUser());
        assertEquals("secret", cliConfiguration.getProxyServerPassword());
    }

}