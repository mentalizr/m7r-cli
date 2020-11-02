package org.mentalizr.cli.util.fileLocator;

public class FileLocatorException extends Exception {

    public FileLocatorException() {
    }

    public FileLocatorException(String message) {
        super(message);
    }

    public FileLocatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileLocatorException(Throwable cause) {
        super(cause);
    }

    public FileLocatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
