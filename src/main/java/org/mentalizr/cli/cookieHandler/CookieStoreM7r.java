package org.mentalizr.cli.cookieHandler;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class CookieStoreM7r implements CookieStore {

    @Override
    public void add(URI uri, HttpCookie cookie) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@get: uri=" + uri);
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@getCookies");
        return null;
    }

    @Override
    public List<URI> getURIs() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@getURIs");
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@add: uri=" + uri.toString() + " cookie=" + cookie.toString());
        return false;
    }

    @Override
    public boolean removeAll() {
        System.out.println(CookieStoreM7r.class.getSimpleName() + "@removeAll");
        return false;
    }
}
