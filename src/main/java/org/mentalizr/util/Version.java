package org.mentalizr.util;

import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.file.SingleValueFile;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.util.fileLocator.FileLocator;
import org.mentalizr.util.fileLocator.FileLocatorException;
import org.mentalizr.util.fileLocator.FileNotLocalizedException;
import org.mentalizr.util.manifestReader.ManifestReader;

import java.io.IOException;

/**
 * Reads version and build information from JAR manifest file.
 * If no manifest is found, an attempt is being made reading the version information
 * from a local file. Build is set to 'unknown' in that case.
 */
public class Version {

    private static final String ATTRIBUTE_VERSION = "Version";
    private static final String ATTRIBUTE_BUILD = "Build";
    private static final String UNKNOWN = "unknown";

    private final String version;
    private final String build;

    private Version(String version, String build) {
        this.version = version;
        this.build = build;
    }

    public static Version getInstance(Class classInJar){
        if (ManifestReader.hasManifest(classInJar)) {
            try {
                ManifestReader manifestReader = new ManifestReader(M7rCli.class);
                String version = manifestReader.getValue(ATTRIBUTE_VERSION);
                if (Strings.isNullOrEmpty(version))
                    throw new RuntimeException("Error on initialization of version info: Attribute '" + ATTRIBUTE_VERSION + "' not found in JAR manifest.");
                String build = manifestReader.getValue(ATTRIBUTE_BUILD);
                if (Strings.isNullOrEmpty(build)) {
                    throw new RuntimeException("Error on initialization of version info: Attribute '" + ATTRIBUTE_BUILD + "' not found in JAR manifest.");
                }
                return new Version(version, build);
            } catch (IOException | FileLocatorException e) {
                throw new RuntimeException("Error on initialization of version info. Error when accessing JAR manifest: " + e.getMessage(), e);
            }
        }

        try {
            FileLocator fileLocator = FileLocator.fromFileSystem("version");
            String version = new SingleValueFile(fileLocator.asPath()).read();
            if (Strings.isNullOrEmpty(version))
                throw new RuntimeException("Error on initialization of version info: No valid data found in local version file.");
            return new Version(version, UNKNOWN);
        } catch (FileNotLocalizedException | IOException e) {
            throw new RuntimeException("Error on initialization of version info. Error when accessing local version file: " + e.getMessage(), e);
        }

    }

    public String getVersion() {
        return this.version;
    }

    public boolean isBuildKnown() {
        return !this.build.equals(UNKNOWN);
    }

    public String getBuild() {
        return this.build;
    }

}
