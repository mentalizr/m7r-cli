package org.mentalizr.cli.fileSystem;

import org.mentalizr.cli.helper.FileReader;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

import java.nio.file.Path;

public class TherapistRestoreSOFS {

    public static TherapistRestoreSO fromFile(Path file) {
        String json = FileReader.fromFile(file);
        return TherapistRestoreSOX.fromJson(json);
    }

}
