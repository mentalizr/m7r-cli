package org.mentalizr.cli.util;

import org.junit.jupiter.api.Test;
import org.mentalizr.cli.util.fileLocator.FileLocator;
import org.mentalizr.cli.util.fileLocator.FileLocatorException;

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