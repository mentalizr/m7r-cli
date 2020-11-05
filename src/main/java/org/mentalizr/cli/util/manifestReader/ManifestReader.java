package org.mentalizr.cli.util.manifestReader;

import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.util.fileLocator.FileLocator;
import org.mentalizr.cli.util.fileLocator.FileLocatorException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestReader {

    private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

    private Attributes attributes;

    public ManifestReader(Class classInJar) throws IOException, FileLocatorException {
        FileLocator fileLocator;
        try {
//            fileLocator = FileLocator.fromClasspath(MANIFEST_PATH);
            fileLocator = FileLocator.fromClasspath(MANIFEST_PATH, classInJar);
        } catch (FileLocatorException e) {
            throw new IllegalStateException("Assertion failed. No JAR manifest found.");
        }

        InputStream inputStream = null;
        try  {
            inputStream = fileLocator.asInputStream();
            Manifest manifest = new Manifest(inputStream);
            this.attributes = manifest.getMainAttributes();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();;
                } catch (IOException ignored) {
                }
            }
        }
    }

    public String getValue(String name) {
        return this.attributes.getValue(name);
    }

    public static boolean hasManifest(Class classInJar) {
        try {
            FileLocator.fromClasspath(MANIFEST_PATH, classInJar);
            return true;
        } catch (FileLocatorException e) {
            return false;
        }
    }

}
