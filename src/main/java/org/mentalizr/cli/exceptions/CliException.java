package org.mentalizr.cli.exceptions;

public class CliException extends RuntimeException {

    public CliException(Throwable cause) {
        super("Error: " + cause.getMessage(), cause);
    }

    public CliException(String message) {
        super(message);
    }

    public CliException(String message, Throwable cause) {
        super(message, cause);
    }
}
