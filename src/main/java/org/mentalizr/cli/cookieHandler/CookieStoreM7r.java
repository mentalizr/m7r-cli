package org.mentalizr.cli.cookieHandler;

import de.arthurpicht.utils.core.collection.Lists;
import org.mentalizr.cli.config.CliConfiguration;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CookieStoreM7r implements CookieStore {

    private CookiePersister cookiePersister;

    public CookieStoreM7r() {
        this.cookiePersister = new CookiePersister();
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());
        this.cookiePersister.save(uri, cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@get: uri=" + uri);

        if (this.cookiePersister.hasCookie(uri)) {
            HttpCookie httpCookie = this.cookiePersister.load();
            return Lists.newArrayList(httpCookie);
        }
        System.out.println("no cookie found!");
        return new ArrayList<>();
    }

    @Override
    public List<HttpCookie> getCookies() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@getCookies");

        if (this.cookiePersister.hasAnyCookie()) {
            HttpCookie httpCookie = this.cookiePersister.load();
            return Lists.newArrayList(httpCookie);
        }
        return new ArrayList<>();
    }

    @Override
    public List<URI> getURIs() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@getURIs");

        if (this.cookiePersister.hasAnyCookie()) {
            String server = this.cookiePersister.getServer();
            try {
                URI uri = new URI(server);
                return Lists.newArrayList(uri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());
        return this.cookiePersister.delete();
    }

    @Override
    public boolean removeAll() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@removeAll");
        return this.cookiePersister.delete();
    }
}
