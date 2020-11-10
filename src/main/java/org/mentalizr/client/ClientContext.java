package org.mentalizr.client;

public class ClientContext {

    private final ClientConfiguration clientConfiguration;
    private final boolean debug;

    public ClientContext(ClientConfiguration clientConfiguration, boolean debug) {
        this.clientConfiguration = clientConfiguration;
        this.debug = debug;
    }

    public ClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    public boolean isDebug() {
        return debug;
    }
}
