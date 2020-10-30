package org.mentalizr.cli.helper;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class FileLocatorTest {

    @Test
    void fromClasspath() {

        try {
            FileLocator.fromClasspath("test.txt");
        } catch (FileLocatorException e) {
            e.printStackTrace();
        }

    }
}