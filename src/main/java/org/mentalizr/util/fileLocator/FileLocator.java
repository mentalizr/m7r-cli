package org.mentalizr.util.fileLocator;

import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLocator {

    private final URI uri;

    public FileLocator(URI uri) {
        AssertMethodPrecondition.parameterNotNull("uri", uri);
        this.uri = uri;
    }

    public FileLocator(URL url) throws FileLocatorException {
        AssertMethodPrecondition.parameterNotNull("url", url);

        try {
            this.uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new FileLocatorException(e);
        }
    }

    public static FileLocator fromClasspath(String filename) throws FileLocatorException {
        URL url = FileLocator.class.getClassLoader().getResource(filename);
        if (url == null) throw new FileNotLocalizedException(filename);
        return new FileLocator(url);
    }

    /**
     * Bind file in same package as specified object.
     *
     * @param fileName
     * @param object
     * @return
     * @throws FileLocatorException
     */
    public static FileLocator fromObjectClasspath(String fileName, Object object) throws FileLocatorException {
        URL url = object.getClass().getResource(fileName);
        if (url == null) throw new FileNotLocalizedException(fileName, object);
        return new FileLocator(url);
    }

    public static FileLocator fromClasspath(String fileName, Class clazz) throws FileLocatorException {
        URL url = clazz.getClassLoader().getResource(fileName);
        if (url == null) throw new FileNotLocalizedException(fileName, clazz);
        return new FileLocator(url);
    }

    public static FileLocator fromFileSystem(String pathname) throws FileNotLocalizedException {
        File file = new File(pathname);
        if (!file.exists()) throw new FileNotLocalizedException(file.getAbsolutePath());
        return new FileLocator(file.toURI());
    }

    public File asFile() {
        return new File(this.uri);
    }

    public Path asPath() {
        return Paths.get(this.uri);
    }

    public InputStream asInputStream() throws FileLocatorException {
        URL url;
        try {
            url = this.uri.toURL();
        } catch (MalformedURLException e) {
            throw new FileLocatorException(e);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new FileLocatorException(e);
        }
    }

}
