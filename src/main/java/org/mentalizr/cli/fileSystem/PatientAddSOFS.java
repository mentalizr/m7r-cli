package org.mentalizr.cli.fileSystem;

import org.mentalizr.cli.helper.FileReader;
import org.mentalizr.serviceObjects.userManagement.PatientAddSO;
import org.mentalizr.serviceObjects.userManagement.PatientAddSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSOX;

import java.nio.file.Path;

public class PatientAddSOFS {

    public static PatientAddSO fromFile(Path file) {
        String json = FileReader.fromFile(file);
        return PatientAddSOX.fromJson(json);
    }

}
