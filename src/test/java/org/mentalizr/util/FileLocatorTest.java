package org.mentalizr.util;

import org.junit.jupiter.api.Test;
import org.mentalizr.util.fileLocator.FileLocator;
import org.mentalizr.util.fileLocator.FileLocatorException;

class FileLocatorTest {

    @Test
    void fromClasspath() {
        try {
            FileLocator.fromClasspath("fileLocator/test.txt");
        } catch (FileLocatorException e) {
            e.printStackTrace();
        }
    }

}