package org.mentalizr.client.cookieHandler;

import de.arthurpicht.utils.core.collection.Lists;
import org.mentalizr.client.RESTCallContext;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CookieStoreM7r implements CookieStore {

    private RESTCallContext restCallContext;
    private CookiePersister cookiePersister;

    public CookieStoreM7r(RESTCallContext restCallContext) {
        this.restCallContext = restCallContext;
        this.cookiePersister = new CookiePersister();
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (this.restCallContext.isDebug())
            System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());
        this.cookiePersister.save(uri, cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        if (this.restCallContext.isDebug())
            System.out.println(CookieStoreM7r.class.getSimpleName() + "@get: uri=" + uri);

        if (this.cookiePersister.hasCookie(uri)) {
            HttpCookie httpCookie = this.cookiePersister.load();
            return Lists.newArrayList(httpCookie);
        }

        if (this.restCallContext.isDebug())
            System.out.println("no cookie found!");

        return new ArrayList<>();
    }

    @Override
    public List<HttpCookie> getCookies() {
        if (this.restCallContext.isDebug())
            System.out.println(CookieStoreM7r.class.getSimpleName() + "@getCookies");

        if (this.cookiePersister.hasAnyCookie()) {
            HttpCookie httpCookie = this.cookiePersister.load();
            return Lists.newArrayList(httpCookie);
        }
        return new ArrayList<>();
    }

    @Override
    public List<URI> getURIs() {
        if (this.restCallContext.isDebug())
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
        if (this.restCallContext.isDebug())
            System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());

        return this.cookiePersister.delete();
    }

    @Override
    public boolean removeAll() {
        if (this.restCallContext.isDebug())
            System.out.println(CookieStoreM7r.class.getSimpleName() + "@removeAll");

        return this.cookiePersister.delete();
    }
}
