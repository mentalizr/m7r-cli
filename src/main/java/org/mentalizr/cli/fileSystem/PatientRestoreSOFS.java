package org.mentalizr.cli.fileSystem;

import org.mentalizr.cli.helper.FileReader;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSOX;

import java.nio.file.Path;

public class PatientRestoreSOFS {

    public static PatientRestoreSO fromFile(Path file) {
        String json = FileReader.fromFile(file);
        return PatientRestoreSOX.fromJson(json);
    }

}
