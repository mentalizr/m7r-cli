package org.mentalizr.cli.config;

public class CliConfiguration {

    private String server;
    private boolean trustAll;
    private String proxyServer;
    private int proxyPort;
    private String proxyServerUser;
    private String proxyServerPassword;

    public CliConfiguration(String server) {
        this.server = server;
        this.trustAll = false;
        this.proxyServer = "";
        this.proxyPort = 0;
        this.proxyServerUser = "";
        this.proxyServerPassword = "";
    }

    public boolean hasProxyServer() {
        return !this.proxyServer.equals("");
    }

    public boolean hasProxyServerCredentials() {
        return !this.proxyServerUser.equals("");
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isTrustAll() {
        return trustAll;
    }

    public void setTrustAll(boolean trustAll) {
        this.trustAll = trustAll;
    }

    public String getProxyServer() {
        return proxyServer;
    }

    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyServerUser() {
        return proxyServerUser;
    }

    public void setProxyServerUser(String proxyServerUser) {
        this.proxyServerUser = proxyServerUser;
    }

    public String getProxyServerPassword() {
        return proxyServerPassword;
    }

    public void setProxyServerPassword(String proxyServerPassword) {
        this.proxyServerPassword = proxyServerPassword;
    }

    @Override
    public String toString() {
        return CliConfiguration.class.getSimpleName() + "[\n"
                + "    server = " + this.server + "\n"
                + "    trustAll = " + this.trustAll + "\n"
                + "    proxyServer = " + this.proxyServer + "\n"
                + "    proxyPort = " + this.proxyPort + "\n"
                + "    proxyServerUser = " + this.proxyServerUser + "\n"
                + "    proxyServerPassword = " + this.proxyServerPassword + "\n"
                + "]";
    }

    public boolean isValid() {
        return !this.server.equals("");
    }

}
