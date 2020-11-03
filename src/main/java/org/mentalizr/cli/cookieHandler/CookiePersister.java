package org.mentalizr.cli.cookieHandler;

import org.mentalizr.cli.ConfFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.util.stringFilePersister.StringFilePersister;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.nio.file.Files;

public class CookiePersister {

    private StringFilePersister cookieNamePersister;
    private StringFilePersister cookieValuePersister;
    private StringFilePersister cookieServerPersister;
    private StringFilePersister cookiePathPersister;

    public CookiePersister() {

        assertSessionDirExists();

        this.cookieNamePersister = new StringFilePersister(ConfFiles.getCookieNameFile());
        this.cookieValuePersister = new StringFilePersister(ConfFiles.getCookieValueFile());
        this.cookieServerPersister = new StringFilePersister(ConfFiles.getCookieServerFile());
        this.cookiePathPersister = new StringFilePersister(ConfFiles.getCookiePathFile());
    }

    private void assertSessionDirExists() {
        if (!ConfFiles.existsSessionDir()) throw new CliException("CLI not initialized.");
    }

    public boolean hasCookie(URI uri) {
        if (!Files.exists(ConfFiles.getCookieDir())) return false;
        String server = null;
        try {
            server = this.cookieServerPersister.read();
        } catch (IOException e) {
            throw new CliException("Error when accessing persistent cookie: " + e.getMessage(), e);
        }
        return server.equals(uri.getHost());
    }

    public boolean hasAnyCookie() {
        return Files.exists(ConfFiles.getCookieDir());
    }

    public void save(URI uri, HttpCookie httpCookie) {
        try {
            if (!Files.exists(ConfFiles.getCookieDir())) {
                Files.createDirectory(ConfFiles.getCookieDir());
            }
            this.cookieNamePersister.write(httpCookie.getName());
            this.cookieValuePersister.write(httpCookie.getValue());
            this.cookieServerPersister.write(uri.getHost());
            this.cookiePathPersister.write(httpCookie.getPath());
        } catch (IOException e) {
            throw new CliException("Error when persisting cookie: " + e.getMessage(), e);
        }
    }

    public HttpCookie load() {
        try {
            String cookieName = this.cookieNamePersister.read();
            String cookieValue = this.cookieValuePersister.read();
            String cookiePath = this.cookiePathPersister.read();

            HttpCookie httpCookie = new HttpCookie(cookieName, cookieValue);
            httpCookie.setVersion(0);
            httpCookie.setPath(cookiePath);

            return  httpCookie;
        } catch (IOException e) {
            throw new CliException("Error when reading cookie: " + e.getMessage(), e);
        }
    }

    public boolean delete() {
        if (!hasAnyCookie()) return false;
        try {
            // TODO better: replace this with recursive delete of cookie dir
            this.cookieNamePersister.deleteIfExists();
            this.cookieValuePersister.deleteIfExists();
            this.cookieServerPersister.deleteIfExists();
            this.cookiePathPersister.deleteIfExists();
            Files.deleteIfExists(ConfFiles.getCookieDir());
            return true;
        } catch (IOException e) {
            throw new CliException("Error when deleting persistent cookie: " + e.getMessage(), e);
        }
    }

    public String getServer() {
        if (!hasAnyCookie()) throw new IllegalStateException("No persistent cookie.");
        try {
            return this.cookieServerPersister.read();
        } catch (IOException e) {
            throw new CliException("Error when accessing persistent cookie: " + e.getMessage(), e);
        }
    }

}
