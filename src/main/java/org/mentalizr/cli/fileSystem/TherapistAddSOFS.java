package org.mentalizr.cli.fileSystem;

import org.mentalizr.cli.helper.FileReader;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSOX;

import java.nio.file.Path;

public class TherapistAddSOFS {

    public static TherapistAddSO fromFile(Path file) {
        String json = FileReader.fromFile(file);
        return TherapistAddSOX.fromJson(json);
    }

}
