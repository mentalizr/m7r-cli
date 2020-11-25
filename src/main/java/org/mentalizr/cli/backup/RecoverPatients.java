package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientRestoreService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSOX;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RecoverPatients {

    public static void exec(RecoverFileLocation recoverFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        List<Path> patientFiles = recoverFileLocation.getAllPatientFiles();

        for (Path file : patientFiles) {

            String patientJson;
            try {
                patientJson = Files.readString(file, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new CliException("Could not read patient file to recover from [" + file.toAbsolutePath() + "]. " + e.getMessage(), e);
            }

            PatientRestoreSO patientRestoreSO = PatientRestoreSOX.fromJson(patientJson);

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
            new PatientRestoreService(patientRestoreSO, restCallContext).call();

            System.out.println("Patient [" + patientRestoreSO.getUuid() + "] restored.");
        }

        System.out.println("[OK] " + patientFiles.size() + " successfully restored.");
    }

}
