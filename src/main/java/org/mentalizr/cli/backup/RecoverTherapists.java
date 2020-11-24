package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.TherapistRestoreService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RecoverTherapists {

    public static void exec(RecoverFileLocation recoverFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        List<Path> therapistFiles = recoverFileLocation.getAllTherapistFiles();

        for (Path therapistFile : therapistFiles) {

            String therapistJson;
            try {
                therapistJson = Files.readString(therapistFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new CliException("Could not read therapist file to recover from [" + therapistFile.toAbsolutePath() + "]. " + e.getMessage(), e);
            }

            TherapistRestoreSO therapistRestoreSO = TherapistRestoreSOX.fromJson(therapistJson);

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
            new TherapistRestoreService(therapistRestoreSO, restCallContext).call();

            System.out.println("Therapist [" + therapistRestoreSO.getUuid() + "] restored.");
        }

        System.out.println("[OK] " + therapistFiles.size() + " successfully restored.");
    }

}
