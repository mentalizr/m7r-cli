package org.mentalizr.client.cookieHandler;

import de.arthurpicht.utils.io.file.SingleValueFile;
import org.mentalizr.cli.ConfFiles;
import org.mentalizr.cli.exceptions.CliException;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.nio.file.Files;

public class CookiePersister {

    private final SingleValueFile cookieNamePersister;
    private final SingleValueFile cookieValuePersister;
    private final SingleValueFile cookieServerPersister;
    private final SingleValueFile cookiePathPersister;

    public CookiePersister() {

        assertSessionDirExists();

        this.cookieNamePersister = new SingleValueFile(ConfFiles.getCookieNameFile());
        this.cookieValuePersister = new SingleValueFile(ConfFiles.getCookieValueFile());
        this.cookieServerPersister = new SingleValueFile(ConfFiles.getCookieServerFile());
        this.cookiePathPersister = new SingleValueFile(ConfFiles.getCookiePathFile());
    }

    private void assertSessionDirExists() {
        if (!ConfFiles.existsSessionDir()) throw new CliException("CLI not initialized.");
    }

    public boolean hasCookie(URI uri) {
        if (!Files.exists(ConfFiles.getCookieDir())) return false;
        String server;
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
