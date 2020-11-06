package org.mentalizr.cli.config;

public class CliCallGlobalConfiguration {

    private boolean debug;
    private boolean silent;
    private boolean stacktrace;

    public CliCallGlobalConfiguration() {
        this.debug = false;
        this.silent = false;
        this.stacktrace = false;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(boolean stacktrace) {
        this.stacktrace = stacktrace;
    }
}
