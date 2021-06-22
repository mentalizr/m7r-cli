package org.mentalizr.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;

import static org.junit.jupiter.api.Assertions.*;

class ZipTest {

    @Test
    public void testZipWithRootDir() throws IOException {
        Path source = Paths.get("src/test/resources/zip");
        System.out.println(source.toAbsolutePath());
        assertTrue(Files.exists(source));

        Path destination = Paths.get("src/test/resources/testZipDir.zip");
        assertTrue(Files.exists(destination.getParent()));
        assertFalse(Files.exists(destination));

        Zip.zip(source, destination, true);

        List<? extends ZipEntry> zipEntryList = Zip.getZipEntryList(destination);
        assertEquals(7, zipEntryList.size());
        assertEquals("zip/", zipEntryList.get(0).getName());
        assertEquals("zip/a/", zipEntryList.get(1).getName());
        assertEquals("zip/a/test_a.txt", zipEntryList.get(2).getName());
        assertEquals("zip/b/", zipEntryList.get(3).getName());
        assertEquals("zip/b/test_b.txt", zipEntryList.get(4).getName());
        assertEquals("zip/test1.txt", zipEntryList.get(5).getName());
        assertEquals("zip/c/", zipEntryList.get(6).getName());

        Files.delete(destination);
    }

    @Test
    public void testZipWithoutRootDir() throws IOException {
        Path source = Paths.get("src/test/resources/zip");
        System.out.println(source.toAbsolutePath());
        assertTrue(Files.exists(source));

        Path destination = Paths.get("src/test/resources/testZipWithoutDir.zip");
        assertTrue(Files.exists(destination.getParent()));
        assertFalse(Files.exists(destination));

        Zip.zip(source, destination, false);

        List<? extends ZipEntry> zipEntryList = Zip.getZipEntryList(destination);
        assertEquals(7, zipEntryList.size());
        assertEquals("/", zipEntryList.get(0).getName());
        assertEquals("a/", zipEntryList.get(1).getName());
        assertEquals("a/test_a.txt", zipEntryList.get(2).getName());
        assertEquals("b/", zipEntryList.get(3).getName());
        assertEquals("b/test_b.txt", zipEntryList.get(4).getName());
        assertEquals("test1.txt", zipEntryList.get(5).getName());
        assertEquals("c/", zipEntryList.get(6).getName());

        Files.delete(destination);
    }

    @Test
    public void testZipSingleFile() throws IOException {
        Path source = Paths.get("src/test/resources/zip/test1.txt");
        System.out.println(source.toAbsolutePath());
        assertTrue(Files.exists(source));

        Path destination = Paths.get("src/test/resources/testZipSingle.zip");
        assertTrue(Files.exists(destination.getParent()));
        assertFalse(Files.exists(destination));

        Zip.zip(source, destination, false);

        List<? extends ZipEntry> zipEntryList = Zip.getZipEntryList(destination);
        assertEquals(1, zipEntryList.size());
        assertEquals("test1.txt", zipEntryList.get(0).getName());

        Files.delete(destination);
    }

}