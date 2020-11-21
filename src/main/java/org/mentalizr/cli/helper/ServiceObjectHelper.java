package org.mentalizr.cli.helper;

import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServiceObjectHelper {

    public static TherapistAddSO therapistAddSOFromFile(String fileName) {

        String json = jsonFromFile(fileName);

        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, TherapistAddSO.class);
    }

    public static TherapistRestoreSO therapistRestoreSOFromFile(String fileName) {
        String json = jsonFromFile(fileName);

        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, TherapistRestoreSO.class);
    }

    private static String jsonFromFile(String fileName) {

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new CliException("Specified file '" + fileName + "' not found.");
        }

        String json;
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            throw new CliException("Could not read file '" + fileName + "': " + e.getMessage(), e);
        }

        return json;
    }


}
