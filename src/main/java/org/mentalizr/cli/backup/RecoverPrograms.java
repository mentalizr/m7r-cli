package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ProgramAddService;
import org.mentalizr.client.restService.userAdmin.TherapistRestoreService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;
import org.mentalizr.serviceObjects.userManagement.ProgramSOX;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSOX;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RecoverPrograms {

    public static void exec(RecoverFileLocation recoverFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        List<Path> programFiles = recoverFileLocation.getAllProgramFiles();

        for (Path programFile : programFiles) {

            String programJson;
            try {
                programJson = Files.readString(programFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new CliException("Could not read program file to recover from. [" + programFile.toAbsolutePath() + "] " + e.getMessage(), e);
            }

            ProgramSO programSO = ProgramSOX.fromJson(programJson);

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
            RestService restService = new ProgramAddService(programSO);
            RestServiceCaller.call(restCallContext, restService);

            System.out.println("Program [" + programSO.getProgramId() + "] restored.");
        }

        System.out.println("[OK] " + programFiles.size() + " successfully restored.");
    }

}
