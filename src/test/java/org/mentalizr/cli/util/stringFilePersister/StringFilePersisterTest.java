package org.mentalizr.cli.util.stringFilePersister;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

class StringFilePersisterTest {

    // If a test fails, delete TEMP_TEST_DIR and all its content before rerunning!

    private static final String TEMP_TEST_DIR = "tempTest";

    @BeforeAll
    static void createTempDir() throws IOException {
        if (!Files.exists(Paths.get(TEMP_TEST_DIR))) {
            Files.createDirectory(Paths.get(TEMP_TEST_DIR));
        }
    }

    @AfterAll
    static void deleteTempDir() throws IOException {
        Files.deleteIfExists(Paths.get(TEMP_TEST_DIR));
    }

    @Test
    void simple() throws IOException {

        String test = "test";
        Path path = Paths.get(TEMP_TEST_DIR, "test.txt");

        StringFilePersister stringFilePersister = new StringFilePersister(path);
        stringFilePersister.write(test);

        String retest = stringFilePersister.read();
        assertEquals(test, retest);

        stringFilePersister.delete();
        assertFalse(Files.exists(path));
    }

    @Test
    void rewrite() throws IOException {

        String test = "test";
        Path path = Paths.get(TEMP_TEST_DIR, "test.txt");

        StringFilePersister stringFilePersister = new StringFilePersister(path);
        stringFilePersister.write("0000000000000");
        stringFilePersister.write(test);

        String retest = stringFilePersister.read();
        assertEquals(test, retest);

        stringFilePersister.delete();
        assertFalse(Files.exists(path));
    }

    @Test
    void exists() throws IOException {
        Path path = Paths.get(TEMP_TEST_DIR, "exist.txt");
        StringFilePersister stringFilePersister = new StringFilePersister(path);
        assertFalse(stringFilePersister.exists());
        stringFilePersister.write("something");
        assertTrue(stringFilePersister.exists());
        stringFilePersister.delete();
        assertFalse(stringFilePersister.exists());
    }

}